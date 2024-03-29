package com.romaalie.budjetointiharjoitustyo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.romaalie.budjetointiharjoitustyo.domain.Kayttaja;
import com.romaalie.budjetointiharjoitustyo.domain.KayttajaRepository;



@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    KayttajaRepository kayttajaRepository;

    @Override
    public UserDetails loadUserByUsername(String nimi) throws UsernameNotFoundException {
        Kayttaja nykyKayttaja = kayttajaRepository.findByNimi(nimi);
        UserDetails kayttaja = new org.springframework.security.core.userdetails.User(nimi, nykyKayttaja.getSalasanaHash(), AuthorityUtils.createAuthorityList(nykyKayttaja.getKayttajaRooli()));
        return kayttaja;
    }
}