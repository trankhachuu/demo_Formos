package formos.demo.executor.dto.response;

import formos.demo.executor.dto.ClientDTO;

public class GetAllCartResponseDTO extends BaseRespone<ClientDTO> {

    private ClientDTO client;

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }
}
