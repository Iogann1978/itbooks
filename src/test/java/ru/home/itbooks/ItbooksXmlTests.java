package ru.home.itbooks;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.home.itbooks.model.xml.ContentItem;
import ru.home.itbooks.model.xml.Contents;
import ru.home.itbooks.service.ContentsService;

import javax.xml.bind.JAXBException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ItbooksXmlTests {

    @Autowired
    private ContentsService contentsService;

    @Test
    public void bookContentsTest() throws JAXBException {
        val list1 = new ArrayList<ContentItem>();
        val list2 = new ArrayList<ContentItem>();
        val list3 = new ArrayList<ContentItem>();
        val list = new ArrayList<ContentItem>() {
            {
                add(new ContentItem("Chapter 1", "1", 5, list1));
                add(new ContentItem("Chapter 2", "2", 15, list2));
                add(new ContentItem("Chapter 3", "3", 20, list3));
            }
        };
        list1.addAll(new ArrayList<ContentItem>() {
            {
                add(new ContentItem("Chapter 1.1", "1.1", 6, null));
                add(new ContentItem("Chapter 1.2", "1.2", 7, null));
                add(new ContentItem("Chapter 1.3", "1.3", 8, null));
            }
        });
        list2.addAll(new ArrayList<ContentItem>() {
            {
                add(new ContentItem("Chapter 2.1", "2.1", 16, null));
                add(new ContentItem("Chapter 2.2", "2.2", 17, null));
            }
        });
        list3.addAll(new ArrayList<ContentItem>() {
            {
                add(new ContentItem("Chapter 3.1", "3.1", 21, null));
            }
        });

        val root1 = new Contents(list);
        val bytes = contentsService.toBytes(root1);
        val xml = new String(bytes, StandardCharsets.UTF_8);
        log.info(xml);
        assertFalse(xml.isEmpty());

        val root2 = contentsService.fromBytes(bytes);
        log.info("root1 size: {}", root2.getItem().size());
        assertEquals(3, root2.getItem().size());
        assertEquals(3, root2.getItem().get(0).getItem().size());
        assertEquals(2, root2.getItem().get(1).getItem().size());
        assertEquals(1, root2.getItem().get(2).getItem().size());
    }
}
