package cn.tanjianff.igoodo.common.util;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
* @ClassName: CaptchaUtil 
* @Description: 关于验证码的工具类
* @date 2016-5-7 上午8:33:08 
* @version 1.0
 */
public final class CaptchaUtil
{
    private CaptchaUtil(){}
    
    /*
     * 随机字符字典
     */
    private static final char[] CHARS = {'0','1','2', '3', '4', '5', '6', '7', '8',
        '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
        'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    
    /*
     * 随机数
     */
    private static Random random = new Random();
    
    /*
     * 获取4位随机数
     */
    private static String getRandomString()
    {
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i <4; i++)
        {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }
    
    /*
     * 获取随机数颜色
     */
    private static Color getRandomColor()
    {
       /* return new Color(random.nextInt(255),random.nextInt(255),
                random.nextInt(255));*/
        return new Color(255,255, 255);//给定白色背景色
    }
    
    /*
     * 返回某颜色的反色
     */
    private static Color getReverseColor(Color c)
    {
        return new Color(255 - c.getRed(), 255 - c.getGreen(),
                255 - c.getBlue());
    }
    
    public static void outputCaptcha(HttpServletRequest request, HttpServletResponse response,StringBuffer codeNum)
            throws ServletException, IOException 
    {

        response.setContentType("image/jpeg");

        String randomString = getRandomString();

        codeNum.append(randomString);//将生成的验证码数字传递出去

        //request.getSession(true).setAttribute("randomString", randomString);//如果需要在服务器上进行验证，则取消注释；而我们采用客户端验证;

        int width = 120;
        int height = 40;

        Color color = getRandomColor();
        Color reverse = getReverseColor(color);

        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        g.setColor(color);
        g.fillRect(0, 0, width, height);
        g.setColor(reverse);
        g.drawString(randomString, 20, 25);
        for (int i = 0, n = random.nextInt(100); i < n; i++) 
        {
            g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
        }

        response.addHeader("ValiCode",randomString);//直接将生成的随机数通过response响应的头部传送出去

        // 转成JPEG格式
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            // 将图片转换为JPEG类型
            //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //encoder.encode(image);
            // 输出图象到页面
            ImageIO.write(bi, "JPEG", response.getOutputStream());
            out.flush();
            //ServletActionContext.getPageContext().pushBody();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
