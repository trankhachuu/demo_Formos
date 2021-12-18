package formos.demo.executor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "stautus")
    private Boolean stautus;

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "orderItems", "category" }, allowSetters = true)
    private Set<Beer> beers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Category id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Category name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStautus() {
        return this.stautus;
    }

    public Category stautus(Boolean stautus) {
        this.setStautus(stautus);
        return this;
    }

    public void setStautus(Boolean stautus) {
        this.stautus = stautus;
    }

    public Set<Beer> getBeers() {
        return this.beers;
    }

    public void setBeers(Set<Beer> beers) {
        if (this.beers != null) {
            this.beers.forEach(i -> i.setCategory(null));
        }
        if (beers != null) {
            beers.forEach(i -> i.setCategory(this));
        }
        this.beers = beers;
    }

    public Category beers(Set<Beer> beers) {
        this.setBeers(beers);
        return this;
    }

    public Category addBeer(Beer beer) {
        this.beers.add(beer);
        beer.setCategory(this);
        return this;
    }

    public Category removeBeer(Beer beer) {
        this.beers.remove(beer);
        beer.setCategory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", stautus='" + getStautus() + "'" +
            "}";
    }
}
