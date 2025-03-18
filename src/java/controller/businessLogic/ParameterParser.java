
package controller.businessLogic;

/**
 * Lớp tiện ích để parse và kiểm tra tham số dạng chuỗi thành các kiểu dữ liệu khác.
 */
public class ParameterParser {

    /**
     * Parse một chuỗi thành số nguyên với kiểm tra hợp lệ.
     *
     * @param param        Chuỗi cần parse
     * @param errorMessage Thông báo lỗi nếu chuỗi không hợp lệ
     * @return Số nguyên được parse từ chuỗi
     * @throws NumberFormatException Nếu chuỗi là null, rỗng, hoặc không phải số
     */
    public static int parseInt(String param, String errorMessage) throws NumberFormatException {
        if (param == null || param.trim().isEmpty()) {
            throw new NumberFormatException(errorMessage);
        }
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(errorMessage);
        }
    }

    // Có thể mở rộng để parse các kiểu dữ liệu khác, ví dụ:
    /**
     * Parse một chuỗi thành số thực với kiểm tra hợp lệ.
     *
     * @param param        Chuỗi cần parse
     * @param errorMessage Thông báo lỗi nếu chuỗi không hợp lệ
     * @return Số thực được parse từ chuỗi
     * @throws NumberFormatException Nếu chuỗi là null, rỗng, hoặc không phải số
     */
    public static double parseDouble(String param, String errorMessage) throws NumberFormatException {
        if (param == null || param.trim().isEmpty()) {
            throw new NumberFormatException(errorMessage);
        }
        try {
            return Double.parseDouble(param);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(errorMessage);
        }
    }
}
