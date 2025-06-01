package data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class DataReader {

    public List<Map<String, String>> getJsonDataToMap() throws IOException {
        String filePath = "//src//test//java//data//PurchaseOrder.json";
        String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<Map<String, String>>>() {
        });

        return data;
    }


}
