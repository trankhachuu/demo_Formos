package formos.demo.executor.web.rest;

import formos.demo.executor.domain.*;
import formos.demo.executor.dto.BeerDTO;
import formos.demo.executor.repository.BeerRepository;
import formos.demo.executor.repository.ClientRepository;
import formos.demo.executor.repository.UserRepository;
import formos.demo.executor.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link formos.demo.executor.domain.Beer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BeerResource {

    private final Logger log = LoggerFactory.getLogger(BeerResource.class);

    private static final String ENTITY_NAME = "beer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeerRepository beerRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public BeerResource(BeerRepository beerRepository, ClientRepository clientRepository, UserRepository userRepository) {
        this.beerRepository = beerRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /beers} : Create a new beer.
     *
     * @param beer the beer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beer, or with status {@code 400 (Bad Request)} if the beer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/beers")
    public ResponseEntity<Beer> createBeer(@Valid @RequestBody Beer beer) throws URISyntaxException {
        log.debug("REST request to save Beer : {}", beer);
        if (beer.getId() != null) {
            throw new BadRequestAlertException("A new beer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Beer result = beerRepository.save(beer);
        return ResponseEntity
            .created(new URI("/api/beers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /beers/:id} : Updates an existing beer.
     *
     * @param id the id of the beer to save.
     * @param beer the beer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beer,
     * or with status {@code 400 (Bad Request)} if the beer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/beers/{id}")
    public ResponseEntity<Beer> updateBeer(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Beer beer)
        throws URISyntaxException {
        log.debug("REST request to update Beer : {}, {}", id, beer);
        if (beer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Beer result = beerRepository.save(beer);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beer.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /beers/:id} : Partial updates given fields of an existing beer, field will ignore if it is null
     *
     * @param id the id of the beer to save.
     * @param beer the beer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beer,
     * or with status {@code 400 (Bad Request)} if the beer is not valid,
     * or with status {@code 404 (Not Found)} if the beer is not found,
     * or with status {@code 500 (Internal Server Error)} if the beer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/beers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Beer> partialUpdateBeer(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Beer beer
    ) throws URISyntaxException {
        log.debug("REST request to partial update Beer partially : {}, {}", id, beer);
        if (beer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, beer.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!beerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Beer> result = beerRepository
            .findById(beer.getId())
            .map(existingBeer -> {
                if (beer.getManufacturer() != null) {
                    existingBeer.setManufacturer(beer.getManufacturer());
                }
                if (beer.getName() != null) {
                    existingBeer.setName(beer.getName());
                }
                if (beer.getCountry() != null) {
                    existingBeer.setCountry(beer.getCountry());
                }
                if (beer.getImage() != null) {
                    existingBeer.setImage(beer.getImage());
                }
                if (beer.getPrice() != null) {
                    existingBeer.setPrice(beer.getPrice());
                }
                if (beer.getDescription() != null) {
                    existingBeer.setDescription(beer.getDescription());
                }
                if (beer.getQuantity() != null) {
                    existingBeer.setQuantity(beer.getQuantity());
                }
                if (beer.getArchive() != null) {
                    existingBeer.setArchive(beer.getArchive());
                }

                return existingBeer;
            })
            .map(beerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beer.getId().toString())
        );
    }

    /**
     * {@code GET  /beers} : get all the beers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beers in body.
     */
    @GetMapping("/beers")
    public ResponseEntity<List<Beer>> getAllBeers(Pageable pageable) {
        log.debug("REST request to get a page of Beers");
        Page<Beer> page = beerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /beers/:id} : get the "id" beer.
     *
     * @param id the id of the beer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beers/{id}")
    public ResponseEntity<Beer> getBeer(@PathVariable Long id) {
        log.debug("REST request to get Beer : {}", id);
        Optional<Beer> beer = beerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(beer);
    }

    /**
     * {@code DELETE  /beers/:id} : delete the "id" beer.
     *
     * @param id the id of the beer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/beers/{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable Long id) {
        log.debug("REST request to delete Beer : {}", id);
        beerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/beers/get-all-beer")
    public ResponseEntity<List<BeerDTO>> getAllBeersMenu() {
        log.debug("REST request to get a page of Beers");
        List<BeerDTO> page = beerToBeerDTO();
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/beers/get-all-beer/{username}")
    public ResponseEntity<List<BeerDTO>> getAllBeersMenu(@PathVariable String username) {
        log.debug("REST request to get a page of Beers");
        List<BeerDTO> page = beerToBeerDTO();

        Optional<User> user = this.userRepository.findOneByLogin(username);
        if (user.isPresent()) {
            Client client = this.clientRepository.getClientByUserId(user.get().getId());
            if (client != null) {
                for (int i = 0; i < page.size(); i++) {
                    page = getUserId(page, client);
                }
            }
        }
        return ResponseEntity.ok().body(page);
    }

    private List<BeerDTO> getUserId(List<BeerDTO> beerList, Client client) {
        for (int i = 0; i < beerList.size(); i++) {
            for (Order order : client.getOrders()) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    Beer beer = orderItem.getBeer();
                    if (beerList.get(i).getId() == beer.getId()) {
                        beerList.get(i).setTotal(order.getTotal().longValue());
                        beerList.get(i).setPrice(order.getPrice());
                        beerList.get(i).setClientID(client.getId());
                        beerList.get(i).setUsername(client.getUser().getLogin());
                        beerList.get(i).setPassword(client.getUser().getPassword());
                    }
                }
            }
        }
        return beerList;
    }

    private List<BeerDTO> beerToBeerDTO() {
        List<Beer> beerList = beerRepository.findAll();
        List<BeerDTO> beerDTOS = new ArrayList<>();
        for (Beer beer : beerList) {
            BeerDTO beerDTO = new BeerDTO();
            beerDTO.setId(beer.getId());
            beerDTO.setManufacturer(beer.getManufacturer());
            beerDTO.setName(beer.getName());
            beerDTO.setCountry(beer.getCountry());
            beerDTO.setImage(beer.getImage());
            beerDTO.setPrice(beer.getPrice());
            beerDTO.setDescription(beer.getDescription());
            beerDTO.setQuantity(beer.getQuantity());
            beerDTO.setArchive(beer.getArchive());
            beerDTO.setOrderItems(beer.getOrderItems());
            beerDTO.setCategory(beer.getCategory());
            beerDTOS.add(beerDTO);
        }
        return beerDTOS;
    }
}
