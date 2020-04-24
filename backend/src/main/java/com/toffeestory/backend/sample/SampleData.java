package com.toffeestory.backend.sample;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountService;
import com.toffeestory.backend.post.Post;
import com.toffeestory.backend.post.PostDtl;
import com.toffeestory.backend.post.PostDtlRepository;
import com.toffeestory.backend.post.PostRepository;
import com.toffeestory.backend.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SampleData implements ApplicationRunner {

    private final AccountService accountService;
    private final PostRepository postRepository;
    private final PostDtlRepository postDtlRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final ToppingCategoryRepository toppingCategoryRepository;
    private final ToppingRepository toppingRepository;
    private final QuantityCodeRepository quantityCodeRepository;
    private final QuantityTypeRepository quantityTypeRepository;
    private final SubToppingRepository subToppingRepository;

    @Value("${url}")
    private String defaultUrl;

    @Value("${product.filePath}")
    private String productFilePath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createAccountHM();

        String productExcelData = Paths.get("").toAbsolutePath().toString().split("ToffeeStory")[0] + "ToffeeStory" + productFilePath;

        // Quantity Data
        createQuantityType(productExcelData);
        createQuantityCode(productExcelData);

        // Product Data
        createProductCategoryData(productExcelData);
        createProductData(productExcelData);

        // Topping Data
        createToppingCategoryData(productExcelData);
        createToppingData(productExcelData);
        createSubToppingData(productExcelData);

        // Todo : productTopping Data 나머지 추가하기
    }

    private void createSubToppingData(String productExcelData) {
        try {
            List<SubTopping> subToppingList = new LinkedList<>();

            FileInputStream excelFile = new FileInputStream(productExcelData);
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheet("SubTopping");

            for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
                // 행 읽기
                XSSFRow row = sheet.getRow(rowIndex);

                if (row == null)
                    break;

                try {
                    SubTopping subTopping = new SubTopping();
                    subTopping.setSubToppingNo((int)row.getCell(0).getNumericCellValue());
                    subTopping.setTopping(toppingRepository.getOne((int)row.getCell(1).getNumericCellValue()));
                    subTopping.setSubToppingName(row.getCell(2).getStringCellValue());
                    subTopping.setDisplayOrder((int)row.getCell(3).getNumericCellValue());
                    subToppingList.add(subTopping);

                } catch (Exception error) {
                    log.error(error.toString());
                }
            }

            subToppingRepository.saveAll(subToppingList);

        } catch (Exception ignored) {
            log.error(ignored.toString());
        }
    }

    private void createToppingData(String productExcelData) {
        try {
            List<Topping> toppingList = new LinkedList<>();

            FileInputStream excelFile = new FileInputStream(productExcelData);
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheet("Topping");

            for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
                // 행 읽기
                XSSFRow row = sheet.getRow(rowIndex);

                if (row == null)
                    break;

                try {
                    Topping topping = new Topping();
                    topping.setToppingNo((int)row.getCell(0).getNumericCellValue());
                    topping.setToppingCategory(toppingCategoryRepository.getOne((int)row.getCell(1).getNumericCellValue()));
                    topping.setToppingName(row.getCell(2).getStringCellValue());
                    topping.setQuantityType(quantityTypeRepository.getOne((int)row.getCell(3).getNumericCellValue()));
                    topping.setToppingPrice(0); // 가격 미정
                    toppingList.add(topping);

                } catch (Exception error) {
                    log.error(error.toString());
                }
            }

            toppingRepository.saveAll(toppingList);

        } catch (Exception ignored) {
            log.error(ignored.toString());
        }

    }

    private void createQuantityCode(String productExcelData) {
        try {

            List<QuantityCode> quantityCodeList = new LinkedList<>();

            FileInputStream excelFile = new FileInputStream(productExcelData);
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheet("QuantityCode");

            for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
                // 행 읽기
                XSSFRow row = sheet.getRow(rowIndex);

                if (row == null)
                    break;

                try {
                    QuantityCode quantityCode = new QuantityCode();
                    quantityCode.setQuantityCodeNo((int)row.getCell(0).getNumericCellValue()); // QuantityCodeNo
                    quantityCode.setQuantityName(row.getCell(1).getStringCellValue()); // QuantityCodeName
                    quantityCode.setQuantityType(row.getCell(2).getStringCellValue()); // QuantityCode Type
                    quantityCodeList.add(quantityCode);

                } catch (Exception error) {
                    log.error(error.toString());
                }
            }

            quantityCodeRepository.saveAll(quantityCodeList);

        } catch (Exception ignored) {
            log.error(ignored.toString());
        }
    }

    private void createQuantityType(String productExcelData) {
        try {

            List<QuantityType> quantityTypeList = new LinkedList<>();

            FileInputStream excelFile = new FileInputStream(productExcelData);
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheet("QuantityType");

            for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
                // 행 읽기
                XSSFRow row = sheet.getRow(rowIndex);

                if (row == null)
                    break;

                try {
                    QuantityType quantityType = new QuantityType();
                    quantityType.setQuantityTypeNo((int)row.getCell(0).getNumericCellValue()); // Quantity TypeNo
                    quantityType.setContent(row.getCell(1).getStringCellValue()); // Quantity Content
                    quantityTypeList.add(quantityType);

                } catch (Exception error) {
                    log.error(error.toString());
                }
            }

            quantityTypeRepository.saveAll(quantityTypeList);

        } catch (Exception ignored) {
            log.error(ignored.toString());
        }
    }

    private void createToppingCategoryData(String productExcelData) {
        try {

            List<ToppingCategory> toppingCategoryList = new LinkedList<>();

            FileInputStream excelFile = new FileInputStream(productExcelData);
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheet("ToppingCategory");

            for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
                // 행 읽기
                XSSFRow row = sheet.getRow(rowIndex);

                if (row == null)
                    break;

                try {
                    ToppingCategory toppingCategory = new ToppingCategory();
                    toppingCategory.setToppingCategoryNo((int)row.getCell(0).getNumericCellValue()); //ToppingCategoryNo
                    toppingCategory.setToppingCategoryName(row.getCell(1).getStringCellValue()); // ToppingCategoryName
                    toppingCategoryList.add(toppingCategory);

                } catch (Exception error) {
                    log.error(error.toString());
                }
            }

            toppingCategoryRepository.saveAll(toppingCategoryList);

        } catch (Exception ignored) {
            log.error(ignored.toString());
        }
    }

    private void createProductData(String productExcelData) {
        try {
            List<Product> productList = new LinkedList<>();
            FileInputStream excelFile = new FileInputStream(productExcelData);
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheet("Product");

            for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
                // 행 읽기
                XSSFRow row = sheet.getRow(rowIndex);

                if (row == null)
                    break;

                try {
                    Product product = new Product();
                    product.setProductNo((int) row.getCell(0).getNumericCellValue()); // ProductNo
                    product.setProductName(row.getCell(1).getStringCellValue()); // ProductName
                    product.setProductCategory(productCategoryRepository.getOne((int) row.getCell(2).getNumericCellValue())); // ProductCategoryNo
                    product.setPrice(0); // ProductPrice
                    productList.add(product);
                } catch (Exception error) {
                    log.error(error.toString());
                }
            }

            productRepository.saveAll(productList);
        } catch (Exception ignored) {
            log.error(ignored.toString());
        }

    }

    private void createProductCategoryData(String productExcelData) {
        try {

            List<ProductCategory> productCategoryList = new LinkedList<>();

            FileInputStream excelFile = new FileInputStream(productExcelData);
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            XSSFSheet sheet = workbook.getSheet("ProductCategory");

            for (int rowIndex = 1; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
                // 행 읽기
                XSSFRow row = sheet.getRow(rowIndex);

                if (row == null)
                    break;

                try {
                    ProductCategory productCategory = new ProductCategory();
                    productCategory.setProductCategoryNo((int) row.getCell(0).getNumericCellValue()); // ProductCategoryNo
                    productCategory.setProductCategoryName(row.getCell(1).getStringCellValue()); // ProductCategoryName
                    productCategoryList.add(productCategory);


                } catch (Exception error) {
                    log.error(error.toString());
                }
            }

            productCategoryRepository.saveAll(productCategoryList);

        } catch (Exception ignored) {
            log.error(ignored.toString());
        }
    }

    private void createAccountHM() {
        Account account = new Account();
        account.setEmail("gunflake09@gmail.com");
        account.setAccountId("gunflake09");
        account.setAccountPwd("qwer1234");
        account.setInstagram("test007");
        account.setTwitter("test009");
        account.setAccountName("Vincent Nam");
        account.setSrc(defaultUrl + "defaultProfile.png");
        accountService.saveAccount(account);

        Account account1 = new Account();
        account1.setEmail("seralee0330@gmail.com");
        account1.setAccountId("seralee");
        account1.setAccountPwd("qwer1234");
        account1.setInstagram("test007");
        account1.setTwitter("test009");
        account1.setAccountName("Sera Lee");
        account1.setSrc(defaultUrl + "defaultProfile.png");
        accountService.saveAccount(account1);

        Account account2 = new Account();
        account2.setEmail("yuri@gmail.com");
        account2.setAccountId("yuri");
        account2.setAccountPwd("qwer1234");
        account2.setInstagram("happy_oriday");
        account2.setTwitter("twitter");
        account2.setAccountName("Yuri");
        account2.setSrc(defaultUrl + "defaultProfile.png");
        accountService.saveAccount(account2);

        Post post = new Post();
        account.setPosts(postRepository.findAllByAccount(account));
        post.setAccount(account);
        post.setSrc(defaultUrl + "postpic1.jpg");
        post.setContent("content");
        post.setScore(3.0f);
        post.setLikeCnt(20);
        post.setPrice((short) 2000);

        List<String> tag = new ArrayList<>();
        tag.add("아메리카노");
        tag.add("카페라떼");
        post.setTags(tag);

        postRepository.save(post);

        PostDtl postdtl = new PostDtl();
        postdtl.setPostNo(1);
        postdtl.setFlag(PostDtl.ToffeeType.PRODUCT);
        postdtl.setToffeeKey(1);
        postdtl.setTagName("아메리카노");

        postDtlRepository.save(postdtl);

        PostDtl postdtl1 = new PostDtl();
        postdtl1.setPostNo(1);
        postdtl1.setFlag(PostDtl.ToffeeType.PRODUCT);
        postdtl1.setToffeeKey(2);
        postdtl1.setTagName("카페라떼");

        postDtlRepository.save(postdtl1);

        Post post2 = new Post();
        account.setPosts(postRepository.findAllByAccount(account));
        post2.setAccount(account);
        post2.setSrc(defaultUrl + "postpic2.jpg");
        post2.setContent("content2");
        post2.setScore(4.0f);
        post2.setLikeCnt(10);
        post2.setPrice((short) 1000);

        List<String> tag2 = new ArrayList<>();
        tag2.add("아메리카노");
        post2.setTags(tag2);

        postRepository.save(post2);

        PostDtl postdtl2 = new PostDtl();
        postdtl2.setPostNo(2);
        postdtl2.setFlag(PostDtl.ToffeeType.PRODUCT);
        postdtl2.setToffeeKey(1);
        postdtl2.setTagName("아메리카노");

        postDtlRepository.save(postdtl2);

        Post post3 = new Post();

        account1.setPosts(postRepository.findAllByAccount(account1));
        post3.setAccount(account1);
        post3.setSrc(defaultUrl + "postpic3.jpg");
        post3.setContent("content3");
        post3.setScore(3.5f);
        post3.setLikeCnt(5);
        post3.setPrice((short) 5000);

        List<String> tag3 = new ArrayList<>();
        tag3.add("바닐라 라떼");
        tag3.add("바닐라 시럽");
        post3.setTags(tag3);

        postRepository.save(post3);

        PostDtl postdtl3 = new PostDtl();
        postdtl3.setPostNo(3);
        postdtl3.setFlag(PostDtl.ToffeeType.PRODUCT);
        postdtl3.setToffeeKey(3);
        postdtl3.setTagName("바닐라 라떼");

        postDtlRepository.save(postdtl3);

        PostDtl postdtl4 = new PostDtl();
        postdtl4.setPostNo(3);
        postdtl4.setFlag(PostDtl.ToffeeType.TOPPING);
        postdtl4.setToffeeKey(1);
        postdtl4.setValue(3);
        postdtl4.setTagName("바닐라 시럽");

        postDtlRepository.save(postdtl4);

        Post post4 = new Post();
        account2.setPosts(postRepository.findAllByAccount(account2));
        post4.setAccount(account2);
        post4.setSrc(defaultUrl + "postpic4.jpg");
        post4.setContent("content4");
        post4.setScore(1.5f);
        post4.setLikeCnt(3);
        post4.setPrice((short) 4000);

        List<String> tag4 = new ArrayList<>();
        tag4.add("화이트 초콜릿 모카");
        tag4.add("화이트모카 시럽");
        tag4.add("휘핑크림");
        post4.setTags(tag4);

        postRepository.save(post4);

        PostDtl postdtl5 = new PostDtl();
        postdtl5.setPostNo(4);
        postdtl5.setFlag(PostDtl.ToffeeType.PRODUCT);
        postdtl5.setToffeeKey(4);
        postdtl5.setTagName("화이트 초콜릿 모카");

        postDtlRepository.save(postdtl5);

        PostDtl postdtl6 = new PostDtl();
        postdtl6.setPostNo(4);
        postdtl6.setFlag(PostDtl.ToffeeType.TOPPING);
        postdtl6.setToffeeKey(2);
        postdtl6.setValue(3);
        postdtl6.setTagName("화이트모카 시럽");

        postDtlRepository.save(postdtl6);

        PostDtl postdtl7 = new PostDtl();
        postdtl7.setPostNo(4);
        postdtl7.setFlag(PostDtl.ToffeeType.TOPPING);
        postdtl7.setToffeeKey(3);
        postdtl7.setSubToppingNo(4);
        postdtl7.setQuantityNo(2);
        postdtl7.setTagName("휘핑크림");

        postDtlRepository.save(postdtl7);

        Post post5 = new Post();
        account1.setPosts(postRepository.findAllByAccount(account1));
        post5.setAccount(account1);
        post5.setSrc(defaultUrl + "postpic5.jpg");
        post5.setContent("content5");
        post5.setScore(3.5f);
        post5.setLikeCnt(25);
        post5.setPrice((short) 2000);

        List<String> tag5 = new ArrayList<>();
        tag5.add("콜드 브루");
        tag5.add("디카페인");
        post5.setTags(tag5);

        postRepository.save(post5);

        PostDtl postdtl8 = new PostDtl();
        postdtl8.setPostNo(5);
        postdtl8.setFlag(PostDtl.ToffeeType.PRODUCT);
        postdtl8.setToffeeKey(5);
        postdtl8.setTagName("콜드 브루");

        postDtlRepository.save(postdtl8);

        PostDtl postdtl9 = new PostDtl();
        postdtl9.setPostNo(5);
        postdtl9.setFlag(PostDtl.ToffeeType.TOPPING);
        postdtl9.setToffeeKey(4);
        postdtl9.setSubToppingNo(1);
        postdtl9.setTagName("디카페인");

        postDtlRepository.save(postdtl9);

        Post post6 = new Post();
        account2.setPosts(postRepository.findAllByAccount(account2));
        post6.setAccount(account2);
        post6.setSrc(defaultUrl + "postpic6.jpg");
        post6.setContent("content6");
        post6.setScore(5.0f);
        post6.setLikeCnt(30);
        post6.setPrice((short) 4000);

        List<String> tag6 = new ArrayList<>();
        tag6.add("스타벅스 돌체 라떼");
        tag6.add("돌체 시럽");
        post6.setTags(tag6);

        postRepository.save(post6);

        PostDtl postdtl10 = new PostDtl();
        postdtl10.setPostNo(6);
        postdtl10.setFlag(PostDtl.ToffeeType.PRODUCT);
        postdtl10.setToffeeKey(6);
        postdtl10.setTagName("스타벅스 돌체 라떼");

        postDtlRepository.save(postdtl10);

        PostDtl postdtl11 = new PostDtl();
        postdtl11.setPostNo(6);
        postdtl11.setFlag(PostDtl.ToffeeType.TOPPING);
        postdtl11.setToffeeKey(5);
        postdtl11.setValue(3);
        postdtl11.setTagName("돌체 시럽");

        postDtlRepository.save(postdtl11);
    }
}
