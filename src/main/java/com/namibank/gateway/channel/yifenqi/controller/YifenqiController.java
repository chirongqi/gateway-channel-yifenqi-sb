package com.namibank.gateway.channel.yifenqi.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.namibank.df.gateway.util.GatewayLogger;
import com.namibank.gateway.channel.yifenqi.dto.PauthResponse;
import com.namibank.gateway.channel.yifenqi.dto.PreauthDTO;
import com.namibank.gateway.channel.yifenqi.dto.TransDTO;
import com.namibank.gateway.channel.yifenqi.service.YifenqiChannelService;

@RestController
@EnableAutoConfiguration
@RequestMapping("yifenqi/service")
public class YifenqiController {

	private static final GatewayLogger logger = GatewayLogger.getLogger(YifenqiController.class);
	@Autowired
	private YifenqiChannelService yifenqiChannelService;

	/**
	 * 信用卡预授权
	 * 
	 * @param preauth
	 * @return
	 */
	@RequestMapping(value = "preauth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TransDTO preauth(@RequestBody PreauthDTO preauth) {
		TransDTO trans = new TransDTO();
		try {
			if (preauth.getTxnAmt() == null) {
				return new TransDTO(false, "交易金金额不能为空");
			}
			if (StringUtils.isEmpty(preauth.getTxnTime())) {
				return new TransDTO(false, "交易时间不能为空");
			}

			if (StringUtils.isEmpty(preauth.getBankCard())) {
				return new TransDTO(false, "信用卡号不能为空");
			}
			if (StringUtils.isEmpty(preauth.getBankUserName())) {
				return new TransDTO(false, "银行卡开户名不能为空");
			}
			if (StringUtils.isEmpty(preauth.getBankUserId())) {
				return new TransDTO(false, "银行卡开户身份证号不能为空");
			}
			if (StringUtils.isEmpty(preauth.getBankPhone())) {
				return new TransDTO(false, "银行预留手机号不能为空");
			}

			PauthResponse pauthResponse = yifenqiChannelService.preauth(preauth);

			trans.setResult(true);
			trans.setData(pauthResponse);
		} catch (Exception e) {
			trans.setResult(false);
			trans.setMsg(e.getMessage());
		}
		logger.info("信用卡预授权 响应数据：{}", JSONObject.toJSONString(trans));
		return trans;
	}

	/**
	 * 信用卡预授权查询
	 * 
	 * @param preauth
	 * @return
	 */
	@RequestMapping(value = "preauthQuery", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TransDTO preauthQuery(@RequestBody PreauthDTO preauth) {
		TransDTO trans = new TransDTO();
		try {
			if (StringUtils.isEmpty(preauth.getTxnTime())) {
				return new TransDTO(false, "交易时间不能为空");
			}
			if (StringUtils.isEmpty(preauth.getBizOrderId())) {
				return new TransDTO(false, "交易订单号不能为空");
			}
			if (StringUtils.isEmpty(preauth.getQueryId())) {
				return new TransDTO(false, "查询id不能为空");
			}
			if (StringUtils.isEmpty(preauth.getTxnType())) {
				return new TransDTO(false, "交易类型不能为空");
			}

			PauthResponse pauthResponse = yifenqiChannelService.preauthQuery(preauth);
			trans.setResult(true);
			trans.setData(pauthResponse);
		} catch (Exception e) {
			trans.setResult(false);
			trans.setMsg(e.getMessage());
		}
		logger.info("信用卡预授权 响应数据：{}", JSONObject.toJSONString(trans));
		return trans;
	}

	/**
	 * 信用卡预授权完成
	 * 
	 * @param preauth
	 * @return
	 */
	@RequestMapping(value = "preauthCompletion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TransDTO preauthCompletion(@RequestBody PreauthDTO preauth) {
		TransDTO trans = new TransDTO();
		try {
			if (preauth.getTxnAmt() == null) {
				return new TransDTO(false, "交易金额不能为空");
			}
			if (StringUtils.isEmpty(preauth.getTxnTime())) {
				return new TransDTO(false, "交易时间不能为空");
			}
			if (StringUtils.isEmpty(preauth.getQueryId())) {
				return new TransDTO(false, "查询id不能为空");
			}
			PauthResponse pauthResponse = yifenqiChannelService.preauthCompletion(preauth);
			trans.setResult(true);
			trans.setData(pauthResponse);
		} catch (Exception e) {
			trans.setResult(false);
			trans.setMsg(e.getMessage());
		}
		logger.info("信用卡预授权 响应数据：{}", JSONObject.toJSONString(trans));
		return trans;
	}

	/**
	 * 信用卡预授权撤销
	 * 
	 * @param preauth
	 * @return
	 */
	@RequestMapping(value = "preauthCancel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TransDTO preauthCancel(@RequestBody PreauthDTO preauth) {
		TransDTO trans = new TransDTO();
		try {
			if (preauth.getTxnAmt() == null) {
				return new TransDTO(false, "交易金额不能为空");
			}
			if (StringUtils.isEmpty(preauth.getTxnTime())) {
				return new TransDTO(false, "交易时间不能为空");
			}
			if (StringUtils.isEmpty(preauth.getQueryId())) {
				return new TransDTO(false, "查询id不能为空");
			}
			PauthResponse pauthResponse = yifenqiChannelService.preauthCancel(preauth);
			trans.setResult(true);
			trans.setData(pauthResponse);
		} catch (Exception e) {
			trans.setResult(false);
			trans.setMsg(e.getMessage());
		}
		logger.info("信用卡预授权 响应数据：{}", JSONObject.toJSONString(trans));
		return trans;
	}

}
