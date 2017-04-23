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
Dưới đây chỉ là một phần nhỏ trong dữ liệu về <tổ hợp âm giữa> mà tôi liệt kê được. Dữ liệu này được tôi sử dụng trong lần đầu tiên cài đặt ứng dụng. Với dữ liệu rất ít này, lần sử dụng đầu tiên ứng dũng phát hiện rất nhiều lỗi chính tả. Sau một thời gian ngắn sử dụng tôi đã tập hợp được khoảng vài trăm <tổ hợp âm giữa> và nó còn đang tăng lên trong quá trình sử dụng. Qua quá trình sử dụng đó ứng dụng đã phát hiện lỗi chính tả ngày càng chính xác hơn. Dữ liệu sẽ được cập nhật thường xuyên để sự trải nghiệm ứng dụng trong lần đầu tiên đạt kết quả tốt nhất.
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
#### 4.1. Chức năng. ####
- **Chức năng tìm kiếm**: Nhập vào một xâu và tìm kiểm theo từng từ đơn => Kết quả trả về là danh sách các từ đó được tìm thấy trong văn bản đi kèm với một từ trước hoặc sau nó. (Có đi kèm với số trang).
- **Chức năng kiểm tra chính tả**: Đây là chức năng chính. Để sử dụng cần mở file văn bản trước tiên. Kết quả trả về là danh sách các từ sai lỗi chính tả. (Có đi kèm với số trang).
- **Chức năng học hỏi từ người dùng**: Để phát triển và hoàn thiện khả năng kiểm tra chính tả của tiếng Việt.
- **Chức năng report**: Do đặc điểm phong phú của tiếng Việt nên không tránh khỏi có những trường hợp xuất hiện các từ sai lỗi chính tả ngoại lệ. Đây là chức năng nhận sự đóng góp từ người dùng. Người dùng sẽ cung cấp một từ cho rằng đó là từ sai lỗi chính tả. Dữ liệu này sẽ được kiểm duyệt và cập nhật.
#### 4.2. Thử nghiệm ####
- Một số hình ảnh về ứng dụng:
![Check Spell](https://scontent.fhan4-1.fna.fbcdn.net/v/t1.0-0/p261x260/18056753_650771051799827_8757446253516002595_n.jpg?oh=839c8fd92b833dbf10ea25a1657b1e06&oe=59999050)
![Check Spell](https://scontent.fhan4-1.fna.fbcdn.net/v/t1.0-0/s526x296/18118470_650771055133160_4852641497824534667_n.jpg?oh=9ba8b198e377e11a5ab8fc3a16cc665d&oe=594D075F)
![Check Spell](https://scontent.fhan4-1.fna.fbcdn.net/v/t1.0-0/s526x296/18056953_650771075133158_7392883524875480846_n.jpg?oh=225980fc2e0dcff29caa714cd1c05b3e&oe=5986BA46)
![Check Spell](https://scontent.fhan4-1.fna.fbcdn.net/v/t1.0-9/18056987_650771081799824_6490378485090576900_n.jpg?oh=5d1e760b86dfe3de8e59524f1e0f5709&oe=5990307B)
---
### 5. Kết luận ###
- Ứng dụng này hiện chưa phải là một ứng dụng hoàn chỉnh và khả năng làm việc với độ chính xác cũng chưa cao. Do thời gian nghiên cứu và làm project có hạn nên không tránh khỏi những thiếu sót trong việc xây dựng ứng dụng. Do tính chất học hỏi của ứng dụng nên ứng dụng  sẽ dần hoàn thiện theo thời gian.
----
Đoàn Anh Tú
Hanoi University of Science and Technology
----
Ha Noi, ngày 23/4/2017
