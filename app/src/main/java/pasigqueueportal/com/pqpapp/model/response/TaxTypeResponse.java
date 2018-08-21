package pasigqueueportal.com.pqpapp.model.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import pasigqueueportal.com.pqpapp.model.data.TaxType;


public class TaxTypeResponse extends  BasicResponse {

    private String result;

    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }


    public List<TaxType> getTaxType() {
        return taxType;
    }

    public void setTaxType(List<TaxType> taxType) {
        this.taxType = taxType;
    }

    @SerializedName("taxtypes")
    private List<TaxType> taxType;






}
