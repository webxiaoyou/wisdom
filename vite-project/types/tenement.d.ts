interface PProperty {
    /**
     * 物业项目地址
     */
    address?: string;
    /**
     * 项目经理姓名
     */
    managerName?: string;
    pageNum?: number;
    pageSize?: number;
    /**
     * 请求参数
     */
    params?: any[];
    /**
     * 物业项目ID
     */
    propertyId?: number;
    /**
     * 物业项目名称
     */
    propertyName?: string;
}

interface PBuilding {
    /**
 * 楼栋ID
 */
    buildingId?: number;
    /**
     * 楼栋编号
     */
    buildingNumber?: string;
    pageNum?: number;
    pageSize?: number;
    /**
     * 请求参数
     */
    params?: any[];
    /**
     * 物业项目ID
     */
    propertyId?: number;
    /**
     * 物业项目名称
     */
    propertyName?: string;
}


interface PFloor {
    /**
     * 楼栋ID
     */
    buildingId?: number | string;
    buildingNumber?: string;
    /**
     * 楼层ID
     */
    floorId?: number;
    /**
     * 楼层编号
     */
    floorNumber?: number;
    pageNum?: number;
    pageSize?: number;
    /**
     * 请求参数
     */
    params?: any[];
}


 interface PPropertyUnit {
    /**
     * 认证状态（0未认证 1已认证 2认证未通过）
     */
    authenticationStatus?: string | number;
    /**
     * 楼栋ID
     */
    buildingId?: number;
    /**
     * 楼栋编号
     */
    buildingNumber?: string;
    /**
     * 创建者
     */
    createBy?: string;
    /**
     * 创建时间
     */
    createTime?: string;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    delFlag?: string;
    /**
     * 楼层ID
     */
    floorId?: number;
    /**
     * 楼层编号
     */
    floorNumber?: number;
    /**
     * 图片地址
     */
    imageUrl?: string;
    /**
     * 业主ID
     */
    ownerId?: number;
    pageNum?: number;
    pageSize?: number;
    /**
     * 请求参数
     */
    params?: any[];
    /**
     * 物业项目ID
     */
    propertyId?: number;
    /**
     * 物业项目名称
     */
    propertyName?: string;
    /**
     * 备注
     */
    remark?: string;
    /**
     * 房屋ID
     */
    unitId?: number;
    /**
     * 房屋编号
     */
    unitNumber?: string;
    /**
     * 更新者
     */
    updateBy?: string;
    /**
     * 更新时间
     */
    updateTime?: string;
    /**
     * 用户姓名
     */
    userName?: string;
}
// 物业报修
 interface PRepair {
    /**
     * 报修位置所在楼栋的唯一标识符
     */
    buildingId?: number;
    /**
     * 报修类别
     */
    category?: string;
    /**
     * 创建人
     */
    createBy?: string;
    /**
     * 创建时间
     */
    createTime?: string;
    /**
     * 报修描述，用户对问题的文字描述
     */
    description?: string;
    /**
     * 报修位置所在楼层的唯一标识符
     */
    floorId?: number;
    /**
     * 报修单处理完成时间
     */
    handledAt?: string;
    /**
     * 处理报修的物业人员的唯一标识符
     */
    handlerId?: number;
    /**
     * 存储上传的图片地址
     */
    imageUrls?: string;
    pageNum?: number;
    pageSize?: number;
    /**
     * 请求参数
     */
    params?: any[];
    /**
     * 物业项目的唯一标识符
     */
    propertyId?: number;
    /**
     * 处理备注，记录物业人员的处理结果或备注信息
     */
    remarks?: string;
    /**
     * 报修单唯一标识符
     */
    repairId?: number;
    /**
     * 报修状态，如待处理、处理中、已完成等
     */
    status?: string;
    /**
     * 更新人
     */
    updateBy?: string;
    /**
     * 更新时间
     */
    updateTime?: string;
    /**
     * 报修用户的唯一标识符
     */
    userId?: number;

    /**
     * 用户手机号
     */
    tel?: string;
}

 interface PComplaintSuggestion {
    /**
     * 投诉建议内容
     */
    content?: string;
    /**
     * 创建人
     */
    createBy?: string;
    /**
     * 创建时间
     */
    createTime?: string;
    /**
     * 投诉建议唯一标识符
     */
    id?: number;
    pageNum?: number;
    pageSize?: number;
    /**
     * 请求参数
     */
    params?: any[];
    /**
     * 物业项目的唯一标识符
     */
    propertyId?: number;
    /**
     * 更新人
     */
    updateBy?: string;
    /**
     * 更新时间
     */
    updateTime?: string;
    /**
     * 用户的唯一标识符
     */
    userId?: number;
}