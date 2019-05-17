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

    private byte[] html;

    @Override
    public void render(Map<String, ?> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        val out = httpServletResponse.getOutputStream();
        if(html != null) {
            out.write(html);
        }
    }

    @Override
    public String getContentType() {
        return "text/html";
    }
}