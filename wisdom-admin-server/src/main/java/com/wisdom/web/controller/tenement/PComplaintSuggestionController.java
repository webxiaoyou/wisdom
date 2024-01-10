package com.wisdom.web.controller.tenement;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.wisdom.common.domain.ResponseResult;
import com.wisdom.common.domain.entity.PComplaintSuggestion;
import com.wisdom.common.domain.dto.PComplaintSuggestionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.wisdom.common.service.PComplaintSuggestionService;
import java.util.List;

/**
 * 
 * 投诉建议信息表
 * 
 * 
 * @Author wisdom
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/pcomplaint_suggestion")
public class PComplaintSuggestionController {

    private final PComplaintSuggestionService pComplaintSuggestionService;

    /**
     * 列表查询
     *
     * @param pComplaintSuggestionDTO 查询条件
     * @return 列表结果
     */
    @SaCheckPermission("tenement:pcomplaint_suggestion:list")
    @GetMapping("/list")
    public ResponseResult selectAll(PComplaintSuggestionDTO pComplaintSuggestionDTO) {
        return pComplaintSuggestionService.selectAll(pComplaintSuggestionDTO);
    }

    /**
     * 根据ID获取详情
     *
     * @param id 主键ID
     * @return 详情结果
     */
    @SaCheckPermission("tenement:pcomplaint_suggestion:query")
    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable Long id) {
        return ResponseResult.okResult(pComplaintSuggestionService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param pComplaintSuggestion 实体对象
     * @return 新增结果
     */
    @SaCheckPermission("tenement:pcomplaint_suggestion:add")
    @PostMapping("/save")
    public ResponseResult saveData(@RequestBody PComplaintSuggestion pComplaintSuggestion) {
        return pComplaintSuggestionService.add(pComplaintSuggestion);
    }

    /**
     * 修改数据
     *
     * @param pComplaintSuggestion 实体对象
     * @return 修改结果
     */
//    @SaCheckPermission("tenement:pcomplaint_suggestion:edit")
    @PutMapping("/update")
    public ResponseResult updateData(@RequestBody PComplaintSuggestion pComplaintSuggestion) {
        return pComplaintSuggestionService.edit(pComplaintSuggestion);
    }

    /**
     * 删除数据
     *
     * @param idList 主键集合
     * @return 删除结果
     */
    @SaCheckPermission("tenement:pcomplaint_suggestion:remove")
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestParam("idList") List<Long> idList) {
        return ResponseResult.okResult(pComplaintSuggestionService.removeByIds(idList));
    }
}


