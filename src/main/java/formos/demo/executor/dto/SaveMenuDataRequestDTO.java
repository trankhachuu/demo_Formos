package formos.demo.executor.dto;

import java.util.List;

public class SaveMenuDataRequestDTO {
    private List<MenuDataRequestDTO> dataRequests;

    public List<MenuDataRequestDTO> getDataRequests() {
        return dataRequests;
    }

    public void setDataRequests(List<MenuDataRequestDTO> dataRequests) {
        this.dataRequests = dataRequests;
    }
}
