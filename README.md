# Check Spell #
## Kiểm tra lỗi chính tả trong tiếng Việt ##

### **1. Cách tiếp cận.** ###
- Kiểm tra lỗi chính tả thông qua bộ từ vựng có sẵn trong từ điển. Nhược điểm của cách tiếp cận này là khả năng mở rộng học các từ mới không có. Phải lưu trữ một lượng lớn từ vựng dẫn đến quá trình kiểm tra có thể chậm.
- Kiểm tra lỗi chính tả dựa trên luật cấu tạo âm tiết tiếng Việt. Đây là cách tiếp cận mà tôi áp dụng trong quá trình xây dựng ứng dụng.

----------
 
### **2. Phân tích cấu trúc âm tiết.** ###
#### 2.1. Cấu trúc âm tiết #### 
Trong tiếng Việt, một âm tiết tiếng Việt được cấu tạo bởi 3 thành phần sau:
- Âm đầu: Âm đầu gồm có tất cả 27 loại: b, c, ch, d, đ, g, gh, h, k, kh, l, m, n, ng, ngh, nh, p, ph, q, r, s, t, th, tr, v, x, none
- Tổ hợp âm giữa: Được cấu tạo bởi một hay hai hoặc 3 nguyên âm kết hợp với nhau cùng với thanh điệu. Số lượng tổ hợp âm giữa là rất lớn không liệt kê một cách thủ công được. Tổ hợp âm giữa sẽ được xây dựng trong quá trình sử dụng ứng dụng của người dùng. VD: a, ai, au, ay, e, eo, i, ia, iai, iày, iá, iáo...
- Âm cuối: Âm cuối có tất cả 9 loại c, ch, m, n, ng, nh, p, t, none
- Kiểm tra lỗi chính tả dựa trên luật cấu tạo âm tiết tiếng Việt. Đây là cách tiếp cận mà tôi áp dụng trong quá trình xây dựng ứng dụng.
#### 2.2. Nguyên âm trong tiếng Việt #### 
Tổ hợp âm giữa ban đầu được tôi xây dựng dựa trên những nguyên âm không có thanh điệu cơ bản của tiếng Việt.
- Có 12 nguyên âm đơn: a, ă, â, e, ê, i, o, ô, ơ, u, ư, y.
- Có 32 nguyên âm đôi, còn gọi là trùng nhị âm (AI, AO, AU, ÂU, AY, ÂY, EO, ÊU, IA, IÊ/YÊ, IU, OA, OĂ, OE, OI, ÔI, ƠI, OO, ÔÔ, UA, UĂ, UÂ, ƯA, UÊ, UI, ƯI,UO, UÔ, UƠ, ƯƠ, ƯU, UY)
- Có 13 nguyên âm ba hay trùng tam âm (IÊU/YÊU, OAI, OAO, OAY, OEO, UAO, UÂY, UÔI, ƯƠI, ƯƠU, UYA, UYÊ, UYU).
----------

### **3. Một số vấn đề về bộ gõ tiếng Việt và Regular Expressions.** ###
- Các bộ gõ tiếng Việt có 1 trong 2 phương thức nhập. Phương thức nhập này do nhà phát triển quyết định và xây dựng.
- 2 kiểu đó là: Single code point mode ("Unicode dựng sẵn"), và combining mark mode ("Unicode tổ hợp").
- Trong ứng dụng mà tôi xây dựng có sử dụng Regular Expressions. Regex chỉ hoạt động tốt được trên nhưng bộ gõ sử dụng Single code point mode.
----------

### **4. Cài đặt ứng dụng và thử nghiệm.** ###