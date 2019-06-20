package ru.home.itbooks.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.xml.ContentItem;
import ru.home.itbooks.model.xml.Contents;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
@Slf4j
public class ContentsService {
    private static JAXBContext context;
    private static Unmarshaller unmarshaller;

    private static Marshaller marshaller;
    static {
        try {
            context = JAXBContext.newInstance(Contents.class);
            unmarshaller = context.createUnmarshaller();
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static Contents fromBytes(byte[] xml) {
        val bais = new ByteArrayInputStream(xml);
        val contents = (Contents) unmarshaller.unmarshal(bais);
        return contents;
    }

    @SneakyThrows
    public static byte[] toBytes(Contents root) {
        val baos = new ByteArrayOutputStream();
        marshaller.marshal(root, baos);
        return  baos.toByteArray();
    }

    public boolean containsXml(Contents root, String xml) {
        boolean flag = false;
        if(root == null || root.getItem() == null || root.getItem().isEmpty() || xml.isEmpty()) {
            return flag;
        }
        for(val item : root.getItem()) {
            flag = checkItem(item, xml);
        }
        return flag;
    }

    private boolean checkItem(ContentItem item, String xml) {
        if(item.getTitle().contains(xml)) {
            return true;
        }
        if(item.getItem() != null && !item.getItem().isEmpty()) {
            for(val titem : item.getItem()) {
                if(checkItem(titem, xml)) {
                    return true;
                }
            }
        }
        return false;
    }
}
