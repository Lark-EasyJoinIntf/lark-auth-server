package com.lark.cloud.auth.configuration;

import com.lark.cloud.user.client.SysUserClient;
import com.lark.cloud.user.dto.SysRoleDto;
import com.lark.cloud.user.dto.SysUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author sunyz
 * @desc
 * @create 2019-01-13 下午2:19
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserClient sysUserClient;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        try {
            SysUserDto sysUserDto=sysUserClient.getByAccount(name);

//            User user = userMapper.findByUsername(name);
//            User user=null;
            if(sysUserDto != null) {
//                List<UserRole> urs = userRoleMapper.findByUserId(user.getId());
                List<SysRoleDto> roleDtos= sysUserClient.listRole(sysUserDto.getId());
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                for(SysRoleDto ur : roleDtos) {
                    String roleName = ur.getCode();
                    SimpleGrantedAuthority grant = new SimpleGrantedAuthority(roleName);
                    authorities.add(grant);
                }
                //封装自定义UserDetails类
                userDetails = new User(sysUserDto.getAccount(),sysUserDto.getPsd(), authorities);
            } else {
                throw new UsernameNotFoundException("该用户不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;
    }
}
