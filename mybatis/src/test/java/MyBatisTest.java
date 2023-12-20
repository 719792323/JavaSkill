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

    @Test
    public void getPeopleById() {
        People people = peopleMapper.getPeopleById(2);
        Assert.assertEquals("zjf", people.getName());
        System.out.println(people);
    }

    @Test
    public void testUpdatePeople() {
        People people = new People(2, "zjf", 100);
        Assert.assertTrue(peopleMapper.updatePeople(people));
    }

    @Test
    public void testInsertPeople() {
        People people = new People("djq", 33);
        Assert.assertTrue(peopleMapper.insertPeople(people));
        Assert.assertNotNull(people.getId());
    }
}
