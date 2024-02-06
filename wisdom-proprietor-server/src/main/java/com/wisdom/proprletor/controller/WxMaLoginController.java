package com.wisdom.proprletor.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wisdom.common.constants.Constants;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.dto.LoginDTO;
import com.wisdom.common.domain.dto.PUserDto;
import com.wisdom.common.domain.entity.PPropertyUnit;
import com.wisdom.common.domain.entity.PUser;
import com.wisdom.common.domain.entity.WxUser;
import com.wisdom.common.domain.model.LoginUser;
import com.wisdom.common.domain.vo.PLoginVo;
import com.wisdom.common.domain.vo.PPropertyUnitAndPUserVo;
import com.wisdom.common.domain.vo.PUserVo;
import com.wisdom.common.enums.DeviceType;
import com.wisdom.common.enums.UserType;
import com.wisdom.common.exception.SystemException;
import com.wisdom.common.helper.LoginHelper;
import com.wisdom.common.service.IPUserService;
import com.wisdom.common.utils.BeanCopyUtils;
import com.wisdom.common.utils.StringUtils;
import com.wisdom.common.utils.file.MimeTypeUtils;
import com.wisdom.system.domain.vo.SysOssVo;
import com.wisdom.system.service.ISysOssService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
public class WxMaLoginController extends ResponseResult {
    private final WxMaService wxMaService;

    private final IPUserService ipUserService;

    private final ISysOssService sysOssService;

//    /**
//     * 登陆接口
//     */
//    @PostMapping("/login")
//    public ResponseResult login(LoginDTO loginDTO) throws WxErrorException {
//        if (StringUtils.isBlank(loginDTO.getAppid()) || StringUtils.isBlank(loginDTO.getCode())) {
//            throw new SystemException("参数不全");
//        }
//
//        if (!wxMaService.switchover(loginDTO.getAppid())) {
//            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", loginDTO.getAppid()));
//        }
//
//        WxMaJscode2SessionResult session = getSession(loginDTO.getAppid(), loginDTO.getCode());
//        //TODO 可以增加自己的逻辑，关联业务相关数据
//        String openid = session.getOpenid();
//        PUserVo pUserVo = ipUserService.getByopenid(openid);
//        //       验证数据库是否有该用户如没有则新增否则查询进行登录
//        if (ObjectUtil.isNull(pUserVo)) {
//            PUserDto userDto = new PUserDto();
//            userDto.setOpenid(openid);
//            userDto.setUtype("1"); //小程序
//            userDto.setLastLoginTime(LocalDateTime.now());
//            userDto.setAvatar(loginDTO.getAvatar());
//            userDto.setNickname(loginDTO.getNickname());
//            PUser pUser = BeanCopyUtils.copyBean(userDto, PUser.class);
//            ipUserService.save(pUser);
//            WxUser wxUser = BeanCopyUtils.copyBean(pUser, WxUser.class);
//            wxUser.setId(pUser.getUserId());
//            wxUser.setAvatar(pUser.getAvatar());
//            wxUser.setNickname(pUser.getNickname());
//            wxUser.setLastLoginTime(LocalDateTime.now());
//            wxUser.setUserType(UserType.APP_USER.getUserType()); //app端用户
//            LoginHelper.loginByDevice(wxUser, DeviceType.XCX);
//            return okResult(new PLoginVo(StpUtil.getTokenValue(), BeanCopyUtils.copyBean(pUser, PUserVo.class), "1"), "登录成功");
//        }
//        WxUser wxUser = BeanCopyUtils.copyBean(pUserVo, WxUser.class);
//        wxUser.setId(pUserVo.getUserId());
//        wxUser.setName(pUserVo.getNickname());
//        wxUser.setUserType(UserType.APP_USER.getUserType());
//        LoginHelper.loginByDevice(wxUser,DeviceType.XCX);
//        return okResult(new PLoginVo(StpUtil.getTokenValue(), pUserVo, "1"),"登录成功");
//    }
    /**
     * 登陆接口
     */
    @PostMapping("/login")
    public ResponseResult login(LoginDTO loginDTO) throws WxErrorException {
        if (StringUtils.isBlank(loginDTO.getAppid()) || StringUtils.isBlank(loginDTO.getCode())) {
            throw new SystemException("参数不全");
        }

        if (!wxMaService.switchover(loginDTO.getAppid())) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", loginDTO.getAppid()));
        }

        WxMaJscode2SessionResult session = getSession(loginDTO.getAppid(), loginDTO.getCode());
        String openid = session.getOpenid();
        PPropertyUnitAndPUserVo pUserVo = ipUserService.getByopenid(openid);

        if (ObjectUtil.isNull(pUserVo)) {
            // 静默登录用户不存在，返回错误信息给前端
            return errorResult(404,"用户未注册，请进行手动登录");
        }

        // 用户存在，进行登录
        WxUser wxUser = BeanCopyUtils.copyBean(pUserVo, WxUser.class);
        wxUser.setId(pUserVo.getUserId());
        wxUser.setName(pUserVo.getNickname());
        wxUser.setUserType(UserType.APP_USER.getUserType());
        LoginHelper.loginByDevice(wxUser, DeviceType.XCX);

        return okResult(new PLoginVo(StpUtil.getTokenValue(), pUserVo), "登录成功");
    }

    /**
     * 手动登录接口，用于用户注册
     */
    @PostMapping("/manualLogin")
    public ResponseResult manualLogin(@RequestBody LoginDTO loginDTO) throws WxErrorException {
        if (StringUtils.isBlank(loginDTO.getAppid()) || StringUtils.isBlank(loginDTO.getCode()) ||
                StringUtils.isBlank(loginDTO.getAvatar()) || StringUtils.isBlank(loginDTO.getNickname())) {
            throw new SystemException("参数不全");
        }

        if (!wxMaService.switchover(loginDTO.getAppid())) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", loginDTO.getAppid()));
        }

        WxMaJscode2SessionResult session = getSession(loginDTO.getAppid(), loginDTO.getCode());
        String openid = session.getOpenid();

        // 用户首次手动登录，保存用户信息到数据库
        PUserDto userDto = new PUserDto();
        userDto.setOpenid(openid);
        userDto.setUtype("1"); // 小程序
        userDto.setLastLoginTime(LocalDateTime.now());
        userDto.setAvatar(loginDTO.getAvatar());
        userDto.setNickname(loginDTO.getNickname());

        PUser pUser = BeanCopyUtils.copyBean(userDto, PUser.class);
        ipUserService.save(pUser);

        // 登录用户
        WxUser wxUser = BeanCopyUtils.copyBean(pUser, WxUser.class);
        wxUser.setId(pUser.getUserId());
        wxUser.setAvatar(pUser.getAvatar());
        wxUser.setNickname(pUser.getNickname());
        wxUser.setLastLoginTime(LocalDateTime.now());
        wxUser.setUserType(UserType.APP_USER.getUserType()); // app端用户
        LoginHelper.loginByDevice(wxUser, DeviceType.XCX);

        return okResult(new PLoginVo(StpUtil.getTokenValue(), BeanCopyUtils.copyBean(pUser, PPropertyUnitAndPUserVo.class)), "登录成功");
    }
    /**
     * 登录结束后自动更新图片接口
     */
    @GetMapping ("/upAvatar")
    public ResponseResult upAvatar(String avatar,Long id,String ossId) throws WxErrorException {
        if (StringUtils.isBlank(avatar)) {
            throw new SystemException("参数不全");
        }
        PUser user = ipUserService.getById(id);
        //       验证数据库是否有该用户如没有则新增否则查询进行登录
        if (ObjectUtil.isNotNull(user)) {
            PUser pUser = new PUser();
            pUser.setUserId(user.getUserId());
            pUser.setAvatar(avatar);
            pUser.setAvatarOssid(ossId);
            ipUserService.updateById(pUser);
            WxUser wxUser = BeanCopyUtils.copyBean(pUser, WxUser.class);
            wxUser.setAvatar(pUser.getAvatar());
            return okResult();
        }
        return errorResult();
    }

    private WxMaJscode2SessionResult getSession(String appid, String code) {
        String openid = "";
        try {
            return wxMaService.getUserService().getSessionInfo(code);
            //TODO 可以增加自己的逻辑，关联业务相关数据
//            return JsonUtils.toJson(session);
        } catch (WxErrorException e) {
            System.out.println(e.getMessage());
            throw new SystemException("微信接口异常");
        } finally {
            WxMaConfigHolder.remove();//清理ThreadLocal
        }
    }

    /**
     * 图片上传
     * @param file
     * @return
     */
//    @SaCheckPermission("system:oss:upload")
    @PostMapping("/upload/image")
    public ResponseResult upload(@RequestPart("file") MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            throw new SystemException("上传文件不能为空");
        }
        String extension = FileUtil.extName(file.getOriginalFilename());
        if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
            throw new SystemException(500,"文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
        }
        SysOssVo oss = sysOssService.upload(file);
        Map<String, String> map = new HashMap<>(2);
        map.put("url", oss.getUrl());
        map.put("fileName", oss.getOriginalName());
        map.put("ossId", oss.getOssId().toString());
        return ResponseResult.okResult(map);
    }
//    @PutMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseResult avatar(@RequestPart("avatarfile") MultipartFile avatarfile) {
//        Map<String, Object> ajax = new HashMap<>();
//        if (!avatarfile.isEmpty()) {
//            String extension = FileUtil.extName(avatarfile.getOriginalFilename());
//            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
//                return ResponseResult.errorResult(500,"文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
//            }
//            SysOssVo oss = sysOssService.upload(avatarfile);
//            String avatar = oss.getUrl();
//            if (userService.updateUserAvatar(LoginHelper.getAccount(), avatar)) {
//                ajax.put("imgUrl", avatar);
//                return ResponseResult.okResult(ajax);
//            }
//        }
//        return ResponseResult.errorResult(500,"上传图片异常，请联系管理员");
//    }

    /**
     * 用户登出
     *
     * @return 登出结果
     */
    @PostMapping("/logout")
    public ResponseResult logout() {
        try {
            StpUtil.logout();
        } catch (NotLoginException ignored) {
        }
        return ResponseResult.okResult();
    }
}
