package com.namibank.gateway.channel.yifenqi.service;

import com.namibank.gateway.channel.yifenqi.dto.PauthResponse;
import com.namibank.gateway.channel.yifenqi.dto.PreauthDTO;

/**
 * 易分期预授权接口
 * 
 * @author chirq
 *
 */
public interface YifenqiChannelService {

	/**
	 * 预授权
	 * 
	 * @param payBean
	 * @return
	 */
	PauthResponse preauth(PreauthDTO preauthDTO);

	/**
	 * 预授权结果查询
	 * 
	 * @param gatePayBean
	 * @return
	 */
	PauthResponse preauthQuery(PreauthDTO preauthDTO);

	/**
	 * 预授权完成
	 * 
	 * @param gatePayBean
	 * @return
	 */
	PauthResponse preauthCompletion(PreauthDTO preauthDTO);

	/**
	 * 预授权撤销
	 * 
	 * @param gatePayBean
	 * @return
	 */
	PauthResponse preauthCancel(PreauthDTO preauthDTO);
}
