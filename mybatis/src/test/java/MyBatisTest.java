import com.mybatis.MyBatisApplication;
import com.mybatis.mapper.PeopleMapper;
import com.mybatis.pojo.People;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

//2.1.4版本
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyBatisApplication.class)
public class MyBatisTest {
    @Resource
    private PeopleMapper peopleMapper;


    @Test
    public void testGetAllPeople() {
        List<People> allPeople = peopleMapper.getAllPeople();
        Assert.assertTrue(!allPeople.isEmpty());
        System.out.println(allPeople);
    }
}
