package com.sparta.nugulpayment.payment.toss;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.nugulpayment.payment.common.TossUtil;
import com.sparta.nugulpayment.payment.dto.request.PostProcessRequest;
import com.sparta.nugulpayment.payment.sqs.dto.SQSApprovePayment;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TossService {
    private static final String WIDGET_SECRET_KEY = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
    @Value("${toss.key.secret-key}")
    private static String API_SECRET_KEY;

    private final TossUtil tossUtil;

    /**
     * (비동기식) 토스 서버에 결제 승인 요청
     *
     * @param approvePayment : 결제 승인에 필요한 객체
     * @return : 결제 승인 결과
     * @throws Exception
     */
    public JSONObject requestTossPayment(SQSApprovePayment approvePayment) throws Exception {
        // 위젯 키는 초기 결제를 생성해서 위젯이 직접 결제할 때 사용됩니다. 그래서 위젯 키는 없어도 될 것 같네요
//        String secretKey = request.getRequestURI().contains("/confirm/payment") ? API_SECRET_KEY : WIDGET_SECRET_KEY;
        ObjectMapper mapper = new ObjectMapper();

        String jsonString = mapper.writeValueAsString(approvePayment);
        return tossUtil.requestPaymentConfirm(jsonString, API_SECRET_KEY);
    }

    /**
     * (동기식) 토스 서버에 결제 승인 요청
     *
     * @param postProcessRequest : 결제 승인에 필요한 객체
     * @return : 결제 승인 결과
     * @throws Exception
     */
    public JSONObject requestPayment(PostProcessRequest postProcessRequest) throws Exception {
        // 위젯 키는 초기 결제를 생성해서 위젯이 직접 결제할 때 사용됩니다. 그래서 위젯 키는 없어도 될 것 같네요
//        String secretKey = request.getRequestURI().contains("/confirm/payment") ? API_SECRET_KEY : WIDGET_SECRET_KEY;
        ObjectMapper mapper = new ObjectMapper();

        String jsonString = mapper.writeValueAsString(postProcessRequest);
        return tossUtil.requestPaymentConfirm(jsonString, API_SECRET_KEY);
    }

}
