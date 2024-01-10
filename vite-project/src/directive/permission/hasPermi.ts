import { Directive, DirectiveBinding } from 'vue';
import { useUserStore } from '@/store/modules/user'
type ExtendedDirective = Directive & {
  $hasPermi: (permissions: string[]) => boolean;
};

const hasPermiDirective: ExtendedDirective = {
  mounted(el, binding) {
    const value = binding.value as string[];
    const allPermission = '*:*:*';
    const permissions = useUserStore().permissions;

    if (value && Array.isArray(value) && value.length > 0) {
      const hasPermissions = permissions.some(
        (permission) => allPermission === permission || value.includes(permission)
      );
      if (!hasPermissions) {
        el.parentNode && el.parentNode.removeChild(el);
      }
    } else {
      console.error('请设置操作权限标签值');
    }
  },

  $hasPermi: function (permissions) {
    const allPermission = '*:*:*';
    const userPermissions = useUserStore().permissions;

    return (
      permissions &&
      Array.isArray(permissions) &&
      permissions.length > 0 &&
      userPermissions.some(
        (permission) => allPermission === permission || permissions.includes(permission)
      )
    );
  },
};
export default hasPermiDirective;