package com.wisdom.web.controller.tenement;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;

import com.wisdom.common.domain.dto.PUserDto;
import com.wisdom.common.domain.entity.PUser;
import com.wisdom.common.service.IPUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小程序用户信息
 *
 * @Author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/tenement/puser")
public class PUserController {

    private final IPUserService pUserService;

    /**
     * 列表查询
     *
     * @param pUserDTO 查询条件
     * @return 列表结果
     */
    @SaCheckPermission("system:puser:list")
    @GetMapping("/list")
    public ResponseResult selectAll(PUserDto pUserDTO) {
        return pUserService.selectAll(pUserDTO);
    }

    /**
     * 根据ID获取详情
     *
     * @param userId 主键ID
     * @return 详情结果
     */
    @SaCheckPermission("system:puser:query")
    @GetMapping(value = "/{userId}")
    public ResponseResult getInfo(@PathVariable Long userId) {


        // 获取用户详情
        PUser user = pUserService.getById(userId);

        // 脱敏处理身份证号
        if (user != null) {
            user.setIdentity(desensitizeIdCard(user.getIdentity()));
        }

        return ResponseResult.okResult(user);
    }
    /**
     * 脱敏处理身份证号
     *
     * @param idCard 原始身份证号
     * @return 脱敏后的身份证号
     */
    private String desensitizeIdCard(String idCard) {
        // 实现你的身份证号脱敏逻辑，例如只显示部分数字
        // 这里仅为示例，实际应根据你的安全策略和需求进行实现
        if (idCard != null && idCard.length() > 8) {
            return idCard.substring(0, 6) + "********" + idCard.substring(idCard.length() - 4);
        }
        return idCard;
    }
    /**
     * 新增数据
     *
     * @param pUser 实体对象
     * @return 新增结果
     */
    @SaCheckPermission("system:puser:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody PUser pUser) {
        return pUserService.add(pUser);
    }

    /**
     * 修改数据
     *
     * @param pUser 实体对象
     * @return 修改结果
     */
    @SaCheckPermission("system:puser:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody PUser pUser) {
        return pUserService.edit(pUser);
    }

    /**
     * 删除数据
     *
     * @param idList 主键集合
     * @return 删除结果
     */
    @SaCheckPermission("system:puser:remove")
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        return ResponseResult.okResult(pUserService.removeByIds(idList));
    }
}


