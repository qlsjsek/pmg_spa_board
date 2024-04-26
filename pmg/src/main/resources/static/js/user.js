//회원가입
let isDuplicateChecked = false;
function signUp() {
	document.getElementById('userId').disabled = false;
	var userId = document.getElementById("userId").value;
	var userPassword = document.getElementById("userPassword").value;
	var confirmPassword = document.getElementById("confirmPassword").value;
	var userName = document.getElementById("userName").value;
	var userAddress = document.getElementById("userAddress").value;
	var userPhone = document.getElementById("userPhone").value;
	var userEmail = document.getElementById("userEmail").value;
	var userBirthDate = document.getElementById("userBirthDate").value;
	if (!userId || !userPassword || !confirmPassword || !userName || !userAddress || !userPhone || !userEmail || !userBirthDate) {
		alert('모든 항목을 입력해주세요!');
		return;
	}

	if (userPassword !== confirmPassword) {
		alert("비밀번호가 일치하지 않습니다.");
		return;
	}

	if (!isDuplicateChecked) {
		alert('중복 체크를 확인해주세요!');
		return;
	}


	var userDto = {
		userId: userId,
		userPassword: userPassword,
		userName: userName,
		userAddress: userAddress,
		userPhone: userPhone,
		userEmail: userEmail,
		userBirthDate: userBirthDate
	}

	fetch('/api/user/signup', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(userDto)
	})
		.then(response => {
			if (response.ok) {
				alert('회원가입 성공');
				document.getElementById('registerPage').style.display = 'none';
				document.getElementById('loginPage').style.display = 'block';
				document.getElementById("userId").value = '';
				document.getElementById("userPassword").value = '';
				document.getElementById("confirmPassword").value = '';
				document.getElementById("userName").value = '';
				document.getElementById("userAddress").value = '';
				document.getElementById("userPhone").value = '';
				document.getElementById("userEmail").value = '';
				document.getElementById("userBirthDate").value = '';
				isDuplicateChecked = false;
			} else {
				throw new Error('회원가입 실패');
			}
		})
		.catch(error => {
			console.error('Error: ', error);
			alert('회원가입 실패');
		});
}
//회원가입 시 중복체크
function checkDuplicate() {
	var userInput = document.getElementById('userId');
	var userId = document.getElementById("userId").value;
	if (!userId) {
		alert("아이디를 입력해주세요");
		return;
	}

	fetch('/api/user/duplicate/' + userId)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('중복 확인 실패');
			}
		})
		.then(data => {
			if (data === true) {
				alert('이미 사용 중인 아이디입니다.');
				document.getElementById('userId').value = '';
				isDuplicateChecked = false;
			} else {
				alert('사용 가능한 아이디입니다.');
				isDuplicateChecked = true;
				userInput.disabled = true;
			}
		})
		.catch(error => {
			console.error('Error: ', error);
			alert('중복 확인 실패');
			isDuplicateChecked = false;
		});
}
//회원정보수정
let checkConfirmPassword = false;
function updateUser() {
	var userId = document.getElementById('updateUserId').value;
	var password = document.getElementById('updatePassword').value;
	var confirmPassword = document.getElementById('updateConfirmPassword').value;
	var address = document.getElementById('updateUserAddress').value;
	var email = document.getElementById('updateUserEmail').value;
	var phone = document.getElementById('updateUserPhone').value;
	if (!password || !confirmPassword) {
		alert('변경할 비밀번호를 입력해주세요');
		return;
	}


	if (password !== confirmPassword) {
		alert('비밀번호가 일치하지 않습니다');
		return;
	}

	if (!checkConfirmPassword) {
		alert('비밀번호 인증을 해주세요');
		return;
	}

	fetch('/api/user/update/' + userId, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			userPassword: password,
			userAddress: address,
			userEmail: email,
			userPhone: phone
		})
	})
		.then(response => {
			if (response.ok) {
				alert('회원 정보 업데이트 성공');
				checkConfirmPassword = false;
				//document.getElementById('editProfilePage').style.display = 'none';
				//document.getElementById('profilePage').style.display = 'block';
				location.reload();
			} else {
				throw new Error('회원 정보 업데이트 실패');
			}
		})
		.catch(error => {
			console.error('Error : ', error);
			alert('회원 정보 업데이트 실패');
		});
}

//회원정보수정 비밀번호 인증
let isPasswordConfirmed = false;
function confirmPassword() {
	var beforeUpdateUserPasswordInput = document.getElementById('beforeUpdateUserPassword');
	var updatePasswordInput = document.getElementById('updatePassword');
	var updateConfirmPasswordInput = document.getElementById('updateConfirmPassword');
	var updateUserPhoneInput = document.getElementById('updateUserPhone');
	var updateUserAddressInput = document.getElementById('updateUserAddress');
	var updateUserEmailInput = document.getElementById('updateUserEmail');
	var userId = document.getElementById('updateUserId').value;
	var userPassword = document.getElementById('beforeUpdateUserPassword').value;

	if (!userPassword) {
		alert('비밀번호를 입력해주세요');
		return;
	}

	fetch('/api/user/confirm', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded'
		},
		body: new URLSearchParams({
			userId: userId,
			userPassword: userPassword
		}).toString()
	})
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('비밀번호 인증 실패');
			}
		})
		.then(data => {
			if (data == true) {
				alert('비밀번호가 일치합니다. 인증 성공!');
				isPasswordConfirmed = true;
				beforeUpdateUserPasswordInput.disabled = true;
				updateConfirmPasswordInput.disabled = false;
				updatePasswordInput.disabled = false;
				updateUserPhoneInput.disabled = false;
				updateUserAddressInput.disabled = false;
				updateUserEmailInput.disabled = false;
				checkConfirmPassword = true;
			} else {
				alert('비밀번호가 일치하지 않습니다.');
				isPasswordConfirmed = false;
				beforeUpdateUserPasswordInput.disabled = false;
			}
		})
		.catch(error => {
			console.error('Error: ', error);
			alert('비밀번호 인증 실패');
			isPasswordConfirmed = false;
			beforeUpdateUserPasswordInput.disabled = false;
		});
}

//로그인
function login() {
	var userId = document.getElementById('loginUserId').value;
	var userPassword = document.getElementById('loginUserPassword').value;
	if (!userId || !userPassword) {
		alert("아이디와 비밀번호를 입력하세요.");
		return;
	}
	var loginData = {
		userId: userId,
		userPassword: userPassword
	}
	fetch('/api/user/login', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(loginData)
	})
		.then(response => {
			if (response.status === 200) {
				alert('로그인 성공');
				document.getElementById('loginPage').style.display = 'none';
				document.getElementById('boardListPage').style.display = 'block';
				document.getElementById('loginUserId').value = '';
				document.getElementById('loginUserPassword').value = '';
				location.reload();
			} else if (response.status === 401 || response.status === 500) {
				alert('아이디 혹은 비밀번호가 틀렸습니다');
			} else {
				alert('존재하지 않는 아이디입니다.');
			}
		})
		.catch(error => {
			console.error('Error: ' + error);
		})
}

//로그아웃
function logout() {
	fetch('/api/user/logout', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		}
	})
		.then(response => {
			if (response.ok) {
				alert('로그아웃 성공');
				location.reload();
			} else {
				alert('로그아웃 실패');
			}
		})
		.catch(error => {
			console.error('로그아웃 요청 실패 : ', error);
		});
}

//회원탈퇴
function deleteUser() {
	var id = document.getElementById('id').value;
	fetch('/api/user/delete/' + id, {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ id: id })
	})
		.then(response => {
			if (response.ok) {
				alert('회원탈퇴 성공');
				document.getElementById('profilePage').style.display = 'none';
				document.getElementById('boardListPage').style.display = 'block';
				location.reload();
			} else {
				alert('회원탈퇴 실패');
			}
		})
		.catch(error => {
			console.error('회원 탈퇴 요청 실패 : ', error);
		})
}
//soft delete 회원탈퇴
function softDeleteUser() {
	var id = document.getElementById('id').value;
	var confirmed = confirm('정말 탈퇴하시겠습니까?');
	if (confirmed) {
		fetch(`/api/user/softDelete/${id}`, {
			method: 'DELETE',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ id: id })
		})
			.then(response => {
				if (response.ok) {
					alert('회원탈퇴 성공');
					logout();
				} else {
					alert('회원탈퇴 실패');
				}
			})
			.catch(error => {
				console.error(error);
			});
	} else {
		alert('취소되었습니다');
	}
}

//아이디 찾기
function findUserId() {
	var userName = document.getElementById('findUserName').value;
	var userPhone = document.getElementById('findUserPhone').value;

	if (!userName) {
		alert('이름을 입력해주세요');
		return;
	}
	if (!userPhone) {
		alert('핸드폰 번호를 입력해주세요');
		return;
	}
	var url = new URL(`/api/user/find/userId`, window.location.origin);
	url.searchParams.append('userName', userName);
	url.searchParams.append('userPhone', userPhone);

	fetch(url, {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json'
		}
	})
		.then(response => {
			if (response.status === 200) {
				return response.text();
			} else {
				return Promise.reject('해당 아이디를 찾을 수 없습니다.');
			}
		})
		.then(data => {
			alert('회원님의 아이디는 : ' + data + ' 입니다.');
			document.getElementById("findUserName").value = '';
			document.getElementById("findUserPhone").value = '';
			document.getElementById('forgotUserIdForm').style.display = 'none';
			document.getElementById('loginPage').style.display = 'block';
		})
		.catch(error => {
			alert('해당 아이디를 찾을 수 없습니다');
			console.error('Error : ' + error);
			document.getElementById("findUserName").value = '';
			document.getElementById("findUserPhone").value = '';
		});
}



function findUserPassword() {
	var userId = document.getElementById('findUserId').value;
	var userPhone = document.getElementById('findUserPwPhone').value;

	if (!userId) {
		alert('아이디를 입력해주세요');
		return;
	}
	if (!userPhone) {
		alert('핸드폰 번호를 입력해주세요');
		return;
	}

	fetch('/api/user/find/userPassword?userId=' + userId + '&userPhone=' + userPhone, {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json'
		}
	})
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('인증 실패');
			}
		})
		.then(data => {
			if (data === true) {
				alert('인증이 확인되었습니다. 비밀번호를 재설정 해주세요');
				document.getElementById('resetPassword').style.visibility = 'visible';
				document.getElementById('confirmResetPassword').style.visibility = 'visible';
				document.getElementById('resetPasswordBtn').style.visibility = 'visible';
				document.getElementById('findUserId').readOnly = true;
				document.getElementById('findUserPwPhone').readOnly = true;
			} else {
				alert('정보가 일치하지 않습니다');
			}
		})
		.catch(error => {
			console.error(error);
		});
}

function resetUserPassword() {
	var userId = document.getElementById('findUserId').value;
	var userPhone = document.getElementById('findUserPwPhone').value;
	var resetPassword = document.getElementById('resetPassword').value;
	var confirmResetPassword = document.getElementById('confirmResetPassword').value;

	if (resetPassword !== confirmResetPassword) {
		alert('비밀번호가 일치하지 않습니다');
		return;
	}

	var requestBody = {
		userId: userId,
		userPhone: userPhone,
		userPassword: resetPassword
	};
	fetch(`/api/user/reset?userId=${userId}&userPhone=${userPhone}&userPassword=${resetPassword}`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(requestBody)
	})
		.then(response => {
			if (response.ok) {
				return response.text();

			} else {
				throw new Error('비밀번호 재설정 실패');
			}
		})
		.then(data => {
			alert(data);
			location.reload();
		})
		.catch(error => {
			console.error(error);
		});

}

