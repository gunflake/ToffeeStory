package com.toffeestory.backend.sample;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountRepository;
import com.toffeestory.backend.account.AccountService;
import com.toffeestory.backend.post.Post;
import com.toffeestory.backend.post.PostDtl;
import com.toffeestory.backend.post.PostDtlRepository;
import com.toffeestory.backend.post.PostRepository;
import com.toffeestory.backend.product.Product;
import com.toffeestory.backend.product.ProductCategory;
import com.toffeestory.backend.product.ProductCategoryRepository;
import com.toffeestory.backend.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class SampleData implements ApplicationRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostDtlRepository postDtlRepository;

    @Value("${url}")
    String defaultUrl;
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createAccountHM();
        createProduct();
    }

    private void createAccountHM() {
        Account account = new Account();
        account.setEmail("gunflake09@gmail.com");
        account.setAccountId("gunflake09");
        account.setAccountPwd("qwer1234");
        account.setInstagram("test007");
        account.setTwitter("test009");
        account.setAccountName("Vincent Nam");
        account.setSrc(defaultUrl+"defaultProfile.png");
        accountService.saveAccount(account);

        Account account1 = new Account();
        account1.setEmail("seralee0330@gmail.com");
        account1.setAccountId("seralee");
        account1.setAccountPwd("qwer1234");
        account1.setInstagram("test007");
        account1.setTwitter("test009");
        account1.setAccountName("Sera Lee");
        account1.setSrc(defaultUrl+"defaultProfile.png");
        accountService.saveAccount(account1);

        Account account2 = new Account();
        account2.setEmail("yuri@gmail.com");
        account2.setAccountId("yuri");
        account2.setAccountPwd("qwer1234");
        account2.setInstagram("happy_oriday");
        account2.setTwitter("twitter");
        account2.setAccountName("Yuri");
        account2.setSrc(defaultUrl+"defaultProfile.png");
        accountService.saveAccount(account2);

        Post post = new Post();
        account.setPosts(postRepository.findAllByAccount(account));
        post.setAccount(account);
        post.setSrc(defaultUrl+"postpic1.jpg");
        post.setContent("content");
        post.setScore(3.0f);
        post.setLikeCnt(20);
        post.setPrice((short)2000);

        List<String> tag = new ArrayList<>();
        tag.add("아메리카노");
        tag.add("카페라떼");
        post.setTags(tag);

        postRepository.save(post);

        PostDtl postdtl = new PostDtl();
        postdtl.setPostNo(1);
        postdtl.setFlag((byte)1);
        postdtl.setToffeeKey(1);
        postdtl.setTagName("아메리카노");

        postDtlRepository.save(postdtl);

        PostDtl postdtl1 = new PostDtl();
        postdtl1.setPostNo(1);
        postdtl1.setFlag((byte)1);
        postdtl1.setToffeeKey(2);
        postdtl1.setTagName("카페라떼");

        postDtlRepository.save(postdtl1);

        Post post2 = new Post();
        account.setPosts(postRepository.findAllByAccount(account));
        post2.setAccount(account);
        post2.setSrc(defaultUrl+"postpic2.jpg");
        post2.setContent("content2");
        post2.setScore(4.0f);
        post2.setLikeCnt(10);
        post2.setPrice((short)1000);

        List<String> tag2 = new ArrayList<>();
        tag2.add("아메리카노");
        post2.setTags(tag2);

        postRepository.save(post2);

        PostDtl postdtl2 = new PostDtl();
        postdtl2.setPostNo(2);
        postdtl2.setFlag((byte)1);
        postdtl2.setToffeeKey(1);
        postdtl2.setTagName("아메리카노");

        postDtlRepository.save(postdtl2);

        Post post3 = new Post();

        account1.setPosts(postRepository.findAllByAccount(account1));
        post3.setAccount(account1);
        post3.setSrc(defaultUrl+"postpic3.jpg");
        post3.setContent("content3");
        post3.setScore(3.5f);
        post3.setLikeCnt(5);
        post3.setPrice((short)5000);

        List<String> tag3 = new ArrayList<>();
        tag3.add("바닐라 라떼");
        tag3.add("바닐라 시럽");
        post3.setTags(tag3);

        postRepository.save(post3);

        PostDtl postdtl3 = new PostDtl();
        postdtl3.setPostNo(3);
        postdtl3.setFlag((byte)1);
        postdtl3.setToffeeKey(3);
        postdtl3.setTagName("바닐라 라떼");

        postDtlRepository.save(postdtl3);

        PostDtl postdtl4 = new PostDtl();
        postdtl4.setPostNo(3);
        postdtl4.setFlag((byte)2);
        postdtl4.setToffeeKey(1);
        postdtl4.setValue(3);
        postdtl4.setTagName("바닐라 시럽");

        postDtlRepository.save(postdtl4);

        Post post4 = new Post();
        account2.setPosts(postRepository.findAllByAccount(account2));
        post4.setAccount(account2);
        post4.setSrc(defaultUrl+"postpic4.jpg");
        post4.setContent("content4");
        post4.setScore(1.5f);
        post4.setLikeCnt(3);
        post4.setPrice((short)4000);

        List<String> tag4 = new ArrayList<>();
        tag4.add("화이트 초콜릿 모카");
        tag4.add("화이트모카 시럽");
        tag4.add("휘핑크림");
        post4.setTags(tag4);

        postRepository.save(post4);

        PostDtl postdtl5 = new PostDtl();
        postdtl5.setPostNo(4);
        postdtl5.setFlag((byte)1);
        postdtl5.setToffeeKey(4);
        postdtl5.setTagName("화이트 초콜릿 모카");

        postDtlRepository.save(postdtl5);

        PostDtl postdtl6 = new PostDtl();
        postdtl6.setPostNo(4);
        postdtl6.setFlag((byte)2);
        postdtl6.setToffeeKey(2);
        postdtl6.setValue(3);
        postdtl6.setTagName("화이트모카 시럽");

        postDtlRepository.save(postdtl6);

        PostDtl postdtl7 = new PostDtl();
        postdtl7.setPostNo(4);
        postdtl7.setFlag((byte)2);
        postdtl7.setToffeeKey(3);
        postdtl7.setSubToppingNo(4);
        postdtl7.setQuantityNo(2);
        postdtl7.setTagName("휘핑크림");

        postDtlRepository.save(postdtl7);

        Post post5 = new Post();
        account1.setPosts(postRepository.findAllByAccount(account1));
        post5.setAccount(account1);
        post5.setSrc(defaultUrl+"postpic5.jpg");
        post5.setContent("content5");
        post5.setScore(3.5f);
        post5.setLikeCnt(25);
        post5.setPrice((short)2000);

        List<String> tag5 = new ArrayList<>();
        tag5.add("콜드 브루");
        tag5.add("디카페인");
        post5.setTags(tag5);

        postRepository.save(post5);

        PostDtl postdtl8 = new PostDtl();
        postdtl8.setPostNo(5);
        postdtl8.setFlag((byte)1);
        postdtl8.setToffeeKey(5);
        postdtl8.setTagName("콜드 브루");

        postDtlRepository.save(postdtl8);

        PostDtl postdtl9 = new PostDtl();
        postdtl9.setPostNo(5);
        postdtl9.setFlag((byte)2);
        postdtl9.setToffeeKey(4);
        postdtl9.setSubToppingNo(1);
        postdtl9.setTagName("디카페인");

        postDtlRepository.save(postdtl9);

        Post post6 = new Post();
        account2.setPosts(postRepository.findAllByAccount(account2));
        post6.setAccount(account2);
        post6.setSrc(defaultUrl+"postpic6.jpg");
        post6.setContent("content6");
        post6.setScore(5.0f);
        post6.setLikeCnt(30);
        post6.setPrice((short)4000);

        List<String> tag6 = new ArrayList<>();
        tag6.add("스타벅스 돌체 라떼");
        tag6.add("돌체 시럽");
        post6.setTags(tag6);

        postRepository.save(post6);

        PostDtl postdtl10 = new PostDtl();
        postdtl10.setPostNo(6);
        postdtl10.setFlag((byte)1);
        postdtl10.setToffeeKey(6);
        postdtl10.setTagName("스타벅스 돌체 라떼");

        postDtlRepository.save(postdtl10);

        PostDtl postdtl11 = new PostDtl();
        postdtl11.setPostNo(6);
        postdtl11.setFlag((byte)2);
        postdtl11.setToffeeKey(5);
        postdtl11.setValue(3);
        postdtl11.setTagName("돌체 시럽");

        postDtlRepository.save(postdtl11);
    }

    private void createProduct() {
        // ProductCategory
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("콜드브루");
        productCategoryRepository.save(productCategory);

        productCategory = new ProductCategory();
        productCategory.setProductCategoryName("에스프레소");
        productCategoryRepository.save(productCategory);

        productCategory = new ProductCategory();
        productCategory.setProductCategoryName("디카페인 커피");
        productCategoryRepository.save(productCategory);

        productCategory = new ProductCategory();
        productCategory.setProductCategoryName("프라푸치노");
        productCategoryRepository.save(productCategory);

        productCategory = new ProductCategory();
        productCategory.setProductCategoryName("블렌디드");
        productCategoryRepository.save(productCategory);

        productCategory = new ProductCategory();
        productCategory.setProductCategoryName("피지오");
        productCategoryRepository.save(productCategory);

        productCategory = new ProductCategory();
        productCategory.setProductCategoryName("티(티바나)");
        productCategoryRepository.save(productCategory);

        productCategory = new ProductCategory();
        productCategory.setProductCategoryName("기타");
        productCategoryRepository.save(productCategory);

        // Product
        Product product = new Product();
//        product.setProductCategoryNo(1);
        product.setProductName("돌체 콜드 브루");
        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(1);
//        product.setProductName("콜드 폼 콜드브루");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(1);
//        product.setProductName("바닐라 크림 콜드 브루");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(1);
//        product.setProductName("콜드 브루");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(1);
//        product.setProductName("나이트로 바닐라 크림");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(1);
//        product.setProductName("나이트로쇼콜라 클라우드");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(1);
//        product.setProductName("나이트로 콜드 브루");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("카페 아메리카노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("카페 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("카푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("카라멜 마키아또");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("화이트 초콜릿 모카");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("커피 스타벅스 더블 샷");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("바닐라 스타벅스 더블샷");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("커라멜 스타벅스 더블 샷");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("헤이즐넛 스타벅스 더블 샷");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("에스프레소");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("에스프레소 마키아또");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("에스크레소 콘 파나");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("바닐라 플랫 화이트");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(2);
//        product.setProductName("스타벅스 돌체 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(3);
//        product.setProductName("디카페인 스타벅스 돌체 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(3);
//        product.setProductName("디카페인 카라멜 마키아또");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(3);
//        product.setProductName("디카페인 카페 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(3);
//        product.setProductName("디카페인 카페 아메리카노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(3);
//        product.setProductName("1/2디카페인 스타벅스 돌체 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(3);
//        product.setProductName("1/2디카페인 카라멜 마키아또");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(3);
//        product.setProductName("1/2디카페인 카페 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(3);
//        product.setProductName("1/2디카페인 카페 아메리카노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(4);
//        product.setProductName("블론드 카라멜 클라우드 마키아또");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(4);
//        product.setProductName("블론드 코코아 클라우드 마키아또");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(4);
//        product.setProductName("블론드 리스트레토 마키아또");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(4);
//        product.setProductName("블론드 에스프레소 토닉");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(4);
//        product.setProductName("블론드 스타벅스 돌체 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(4);
//        product.setProductName("블론드 카페 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(4);
//        product.setProductName("블론드 카페 아메리카노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("이천 햅쌀 커피 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("자파칩 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("화이트 초콜릿 모카 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("카라멜 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("모카 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("에스프레소 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("이천 햅쌀 크림 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("그린 티 크림 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("초콜릿 크림 칩 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("초콜릿 크림 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("바닐라 크림 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("제주 까망 크림 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("제주 쑥떡 크림 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("제주 말차샷 크림 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(5);
//        product.setProductName("화이트 타이거 프라푸치노");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(6);
//        product.setProductName("망고 바나나 블렌디드");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(6);
//        product.setProductName("자몽 셔벗 블렌디드");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(6);
//        product.setProductName("딸기 요거트 블렌디드");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(6);
//        product.setProductName("망고 패션 후르츠 블렌디드");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(6);
//        product.setProductName("익스트림 티 블렌디드");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(7);
//        product.setProductName("핑크 자몽 피지오");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(7);
//        product.setProductName("쿨 라임 피지오");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(7);
//        product.setProductName("블랙 티 레모네이드 피지오");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(7);
//        product.setProductName("패션 탱고 티 레모네이드 피지오");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(7);
//        product.setProductName("매직 팝 스플래쉬 피지오");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("그린 티 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("라임 패션 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("자몽 허니 블랙 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("차이 티 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("제주 유기 녹차");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("잉글리쉬 프렉퍼스트 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("차이 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("유스베리 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("히비스커스 블렌드 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("민트 블렌드 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("캐모마일 블렌드 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("제주 유자 탱고 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("제주 말차샷 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("그랜마 애플 블랙 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("그랜마 애플 블랙 밀크 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("돌체 블랙 밀크 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(8);
//        product.setProductName("유자 민트 티");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(9);
//        product.setProductName("시크니처 핫 초콜릿");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(9);
//        product.setProductName("스팀 우유");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(9);
//        product.setProductName("우유");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(9);
//        product.setProductName("제주 노랑 고구마 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(9);
//        product.setProductName("제주 쑥쑥 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(9);
//        product.setProductName("제주 까망 라떼");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(9);
//        product.setProductName("플러피 판다 핫 초콜릿");
//        productRepository.save(product);
//        product = new Product();
//        product.setProductCategoryNo(9);
//        product.setProductName("매직 윈터 트로피컬 핫 펀치");
//        productRepository.save(product);
    }
}
