//회원가입
let isDuplicateChecked = false;
function signUp() {
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
			}
		})
		.catch(error => {
			console.error('Error: ', error);
			alert('중복 확인 실패');
			isDuplicateChecked = false;
		});
}

function confirmPassword() {
	var confirmUserPassword = document.getElementById('confirmUserPassword').value;
	var userPassword = document.getElementById('beforeUpdateUserPassword').value;
	if (!userPassword) {
		alert('비밀번호를 입력해주세요');
		return;
	}
	fetch('/api/user/confirm/' + userPassword)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('비밀번호 인증 실패');
			}
		})
		.then(data => {
			if (data === true) {
				if (userPassword === confirmUserPassword) {
					alert('비밀번호가 일치합니다. 인증 성공!');
				} else {
					alert('비밀번호가 일치하지 않습니다.');
				}
			} else {
				alert('비밀번호가 일치하지 않습니다.');
			}
		})
		.catch(error => {
			console.error('Error: ', error);
			alert('비밀번호 인증 실패');
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
	};

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
	console.log(id);
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


