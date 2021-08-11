import cn.brightstorage.App;
import cn.brightstorage.model.entity.Category;
import cn.brightstorage.model.entity.Relation;
import cn.brightstorage.model.entity.StorageUnit;
import cn.brightstorage.model.entity.User;
import cn.brightstorage.repository.CategoryRepository;
import cn.brightstorage.repository.RelationRepository;
import cn.brightstorage.repository.StorageUnitRepository;
import cn.brightstorage.repository.UserRepository;
import cn.brightstorage.utils.AESUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class BSTest {

    @Resource
    UserRepository userRepository;
    @Resource
    RelationRepository relationRepository;
    @Resource
    CategoryRepository categoryRepository;
    @Resource
    StorageUnitRepository storageUnitRepository;
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void insertTestUser(){
//        String bcp = BCrypt.hashpw("123456");
//        for(int i = 0;i<9;i++){
//            User user = new User();
//            user.setPhone("1580000000" + i);
//            user.setPassword(bcp);
//            userRepository.save(user);
//        }
//    }

//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void select(){
//        User user = userRepository.findById("190e6932-c494-4cf1-832a-459e12c9bd3d").get();
////        for(Relation relation: user.getRelations()){
////            System.out.println(relation.getName());
////        }
//
//        Relation r = relationRepository.findById(1L).get();
//        user.getRelations().removeIf(relation -> relation.getId().equals(2L));
//        userRepository.save(user);
//
//    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void insertCategory() {
        Category c = new Category();
        User user = userRepository.findById("190e6932-c494-4cf1-832a-459e12c9bd3d").get();
        c.setOwner(user);
        c.setName("食品");
        categoryRepository.save(c);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void insertStorageUnit() {
        StorageUnit su = new StorageUnit();
        User user = userRepository.findById("190e6932-c494-4cf1-832a-459e12c9bd3d").get();
        Category c = categoryRepository.findById(3L).get();
        su.setOwner(user);
        su.setName("面包");
        su.setCategories(new HashSet<Category>() {
            {
                add(c);
            }
        });
        storageUnitRepository.save(su);
    }
}
