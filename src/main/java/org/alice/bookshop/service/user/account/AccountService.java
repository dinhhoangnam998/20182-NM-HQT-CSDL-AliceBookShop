package org.alice.bookshop.service.user.account;

import java.util.ArrayList;
import java.util.List;

import org.alice.bookshop.model.User;
import org.alice.bookshop.service.utility.UserUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("uaAccountService")
public class AccountService extends UserUtilityService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private AccountChecker accChecker;

	public List<String> validateSignUpAccount(User user) {
		List<String> errMsgs = validateModifyProfile(user);
		String temp;

		temp = accChecker.checkUsername(user.getUsername());
		if (!temp.equals("ok")) {
			errMsgs.add(temp);
		}
		return errMsgs;
	}

	public void signup(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userJpa.save(user);
	}

	public List<String> validateModifyProfile(User user) {
		List<String> msgs = new ArrayList<>();
		String temp;

		temp = accChecker.checkPassword(user.getPassword(), user.getConfirmPassword());
		if (!temp.equals("ok")) {
			msgs.add(temp);
		}

		temp = accChecker.checkEmail(user.getEmail());
		if (!temp.equals("ok")) {
			msgs.add(temp);
		}

		temp = accChecker.checkPhone(user.getPhone());
		if (!temp.equals("ok")) {
			msgs.add(temp);
		}

		return msgs;
	}

}
