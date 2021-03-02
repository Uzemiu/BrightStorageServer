package cn.brightstorage.utils;

import cn.brightstorage.exception.InternalException;
import cn.brightstorage.model.support.VerificationCode;
import cn.hutool.core.img.ImgUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QRCodeUtil {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final String FORMAT = "jpg";
    private static final Map<EncodeHintType, Object> HINTS = new HashMap<>();

    static {
        HINTS.put(EncodeHintType.CHARACTER_SET, "utf-8");
        HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        HINTS.put(EncodeHintType.MARGIN, 2);
    }

    public static BufferedImage asBufferedImage(String content, int width, int height){
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, HINTS);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            throw new InternalException("生成二维码失败");
        }
    }

    public static VerificationCode generateInviteQRCode(Long relationId){
        String uuid = UUID.randomUUID().toString();
        // todo
        BufferedImage codeImage = asBufferedImage(uuid, WIDTH, HEIGHT);
        String base64 = ImgUtil.toBase64(codeImage, FORMAT);

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(relationId.toString());
        verificationCode.setUuid(uuid);
        verificationCode.setEntity(base64);
        return verificationCode;
    }
}
