package com.github.axinger;

import cn.hutool.core.io.FileUtil;
import com.deepoove.poi.XWPFTemplate;
import lombok.SneakyThrows;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

class WriteTextToDocTest {


    @SneakyThrows
    @Test
    void test1() {

        ClassPathResource resource = new ClassPathResource("file/test01.docx");

        try (InputStream inputStream = resource.getInputStream()) {
            // 一行代码,写入占位符
            XWPFTemplate template = XWPFTemplate.compile(inputStream).render(new HashMap<>() {{
                put("title", "Poi-tl 模板引擎");
                put("name", "张三");
                put("sex", "男");
            }});

            String path = FileUtil.createTempFile("writeToDoc", ".docx", true).getPath();
            System.out.println("path = " + path);
            template.writeToFile(path);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Test
    void test2() {


        ClassPathResource resource = new ClassPathResource("file/test01.docx");


        String outputImagePath = "output.png"; // 输出的 PNG 图片路径

        try {
            // 读取 .docx 文件
//            FileInputStream fis = resource.getInputStream();

            InputStream fis = resource.getInputStream();

            XWPFDocument document = new XWPFDocument(fis);

            // 提取文档内容
            StringBuilder content = new StringBuilder();
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                for (XWPFRun run : paragraph.getRuns()) {
                    content.append(run.getText(0));
                }
                content.append("\n"); // 换行
            }

            System.out.println("content = " + content);


            // 将内容渲染为图片
            BufferedImage image = renderTextToImage(content.toString(), 800, 600); // 图片尺寸

            // 保存为 PNG 文件
            FileOutputStream fos = new FileOutputStream(outputImagePath);
            ImageIO.write(image, "png", fos);
            fos.close();

            System.out.println("图片生成成功：" + outputImagePath);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 将文字渲染为图片
     *
     * @param text   文字内容
     * @param width  图片宽度
     * @param height 图片高度
     * @return 生成的图片
     */
    private BufferedImage renderTextToImage(String text, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 设置背景颜色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 设置字体和颜色
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));

        // 绘制文字
        String[] lines = text.split("\n");
        int y = 20; // 初始 Y 坐标
        for (String line : lines) {
            g2d.drawString(line, 10, y); // 左对齐
            y += 20; // 每行间距
        }

        g2d.dispose();
        return image;
    }


}
