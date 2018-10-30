import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class JSONTester {

    public static void main(String[] args) {
        String s = "{\"Timestamp\":\"1539566018\",\"DeviceID\":\"DEV0000001636\",\"EndPoints\":{\"0\":{\"0\":0,\"1\":0}}}";
        JSONObject obj = new JSONObject(s);
        JSONObject e = (JSONObject) obj.get("EndPoints");
        Map<String, Object> map = e.toMap();
        System.out.println("sdsdsds");
    }
}
