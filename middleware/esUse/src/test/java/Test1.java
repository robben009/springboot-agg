import com.es.ESApplication;
import com.es.dao.ESBaseDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = ESApplication.class)
public class Test1 {

    @Autowired
    private ESBaseDao esBaseDao;

    @Test
    public void test(){
        String result = esBaseDao.getByJsonString("POST", "url",null);
        System.out.println(result);
    }


}
