package ru.home.itbooks.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BytesView implements View {

    private static final String contentType = "text/html";
    private byte[] html;

    @Override
    public void render(Map<String, ?> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(contentType);
        val out = response.getOutputStream();
        if(html != null) {
            out.write(html);
        }
    }

    @Override
    public String getContentType() {
        return contentType;
    }
}