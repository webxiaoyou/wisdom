 interface Activity {
    /**
     * 活动唯一标识符
     */
    activityId?: number;
    /**
     * 活动内容
     */
    content?: string;
    /**
     * 活动封面URL或文件路径
     */
    coverUrl?: any;
    /**
     * 创建者
     */
    createBy?: string;
    /**
     * 创建时间
     */
    createTime?: string;
    /**
     * 活动描述
     */
    description?: string;
    /**
     * 活动结束时间
     */
    endTime?: string;
    /**
     * 活动地点
     */
    location?: string;
    /**
     * 最大参与人数
     */
    maxParticipants?: number;
    pageNum?: number;
    pageSize?: number;
    /**
     * 开始时间与结束时间
     */
    params?: any;
    /**
     * 物业项目的唯一标识符
     */
    propertyId?: number;
    /**
     * 报名结束时间
     */
    registrationEndTime?: string;
    /**
     * 报名开始时间
     */
    registrationStartTime?: string;
    /**
     * 活动开始时间
     */
    startTime?: string;
    /**
     * 活动标题
     */
    title?: string;
    /**
     * 更新者
     */
    updateBy?: string;
    /**
     * 更新时间
     */
    updateTime?: string;
}