package ru.home.itbooks.service;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.home.itbooks.model.Contents;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

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

    public static Contents fromBytes(byte[] xml) {
        Contents contents = null;
        try {
            val bais = new ByteArrayInputStream(xml);
            contents = (Contents) unmarshaller.unmarshal(bais);
        } catch (JAXBException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return contents;
    }
}
