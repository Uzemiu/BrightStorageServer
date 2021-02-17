import cn.brightstorage.App;
import cn.brightstorage.model.entity.Relation;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.RelationRepository;
import cn.brightstorage.repository.UserRepository;
import cn.brightstorage.utils.AESUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class BSTest {

    @Resource
    UserRepository userRepository;
    @Resource
    RelationRepository relationRepository;
    @Resource
    AESUtil aesUtil;

    @Test
    public void aes(){
        System.out.println(aesUtil.encrypt("123456"));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void insertTestUser(){
        String bcp = BCrypt.hashpw("123456");

        for(int i = 0;i<9;i++){
            User user = new User();
            user.setPhone("1580000000" + i);
            user.setPassword(bcp);
            userRepository.save(user);
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void select(){
        User user = userRepository.findById("29bff538-00cf-4cea-a905-cdde9b908f2e").get();
//        for(Relation relation: user.getRelations()){
//            System.out.println(relation.getName());
//        }

        Relation r = relationRepository.findById(1L).get();
        user.getRelations().removeIf(relation -> relation.getId().equals(2L));
        userRepository.save(user);

    }
}
