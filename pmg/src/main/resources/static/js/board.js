//게시글 작성
function createBoard() {
	var selectElement = document.getElementById('categorySelect');
	var categoryId = selectElement.value;
	var boardTitle = document.getElementById('boardTitle').value;
	var boardContent = document.getElementById('boardContent').value;
	var boardImage = document.getElementById('boardImage').files[0];
	var userId = document.getElementById('boardCreateUserId').value;
	console.log(userId);

	if (!boardTitle) {
		alert('제목을 입력해주세요');
		return;
	}
	if (!boardContent) {
		alert('내용을 입력해주세요');
		return;
	}

	var formData = new FormData();
	formData.append('categoryId', categoryId);
	formData.append('boardTitle', boardTitle);
	formData.append('boardContent', boardContent);
	formData.append('boardImage', boardImage);
	formData.append('userId', userId);

	fetch('/api/board/create', {
		method: 'POST',
		body: formData
	})
		.then(response => {
			if (response.ok) {
				alert('게시글이 성공적으로 작성되었습니다');
				document.getElementById('boardWritePage').style.display = 'none';
				document.getElementById('boardListPage').style.display = 'block';
				location.reload();
			} else {
				console.error('Error:', response.status);
				alert('게시글 등록 실패');
			}
		})
		.catch(error => {
			console.error('Error:', error);
			alert('게시글 등록 실패');
		});
}
//게시글 상세페이지
function goToBoardDetail(event) {
	const boardId = event.target.getAttribute('data-board-id');

	findUserIdByBoardId(boardId)
		.then(userId => {
			console.log('게시물 작성자 userId:', userId);

			const userIdElement = document.getElementById('detailUserId');
			const detailUserId = userIdElement ? userIdElement.value : null;

			console.log('detailUserId:', detailUserId);

			if (detailUserId !== userId) {
				document.getElementById('updateBtn').style.display = 'none';
				document.getElementById('deleteBtn').style.display = 'none';
			}

			document.getElementById('boardDetailId').value = boardId;
			event.preventDefault();

			fetch('/api/board/detail/' + boardId)
				.then(response => {
					if (response.ok) {
						return response.json();
					} else {
						throw new Error('게시글을 가져오는데 실패했습니다.');
					}
				})
				.then(boardData => {
					goToBoardDetail1();
					increaseReadCount(boardId);
					const titleElement = boardData.boardTitle;
					const contentElement = boardData.boardContent;
					const recommendElement = boardData.boardRecommend;

					const boardDetailTitleElement = document.getElementById('boardDetailTitle');
					const boardDetailContentElement = document.getElementById('boardDetailContent');
					const boardDetailRecommendElement = document.getElementById('boardDetailRecommend');
					boardDetailTitleElement.textContent = titleElement;
					boardDetailContentElement.textContent = contentElement;
					boardDetailRecommendElement.textContent = recommendElement;
					findBoardImage();
					document.getElementById('boardUpdateTitle').value = titleElement;
					document.getElementById('boardUpdateContent').value = contentElement;

				})
				.catch(error => {
					console.error('Error:', error);
				});
		})
		.catch(error => {
			console.error('Error:', error);
		});
}
//board에서 userId 찾기
function findUserIdByBoardId(boardId) {
	return fetch(`/api/board/userId/${boardId}`)
		.then(response => {
			if (response.ok) {
				return response.text();
			} else {
				throw new Error('게시물 작성자 정보를 가져오는데 실패했습니다');
			}
		})
		.catch(error => {
			throw new Error('게시물 작성자 정보를 가져오는데 실패했습니다');
		});
}
//board에서 categoryName 찾기
function findBoardCategoryNameByBoardId(boardId) {
	return fetch(`/api/board/categoryName/${boardId}`)
		.then(response => {
			if (response.ok) {
				return response.text();
			} else {
				throw new Error('카테고리 이름을 가져오는데 실패했습니다');
			}
		})
		.catch(error => {
			throw new Error('카테고리 이름을 가져오는데 실패했습니다');
		});
}

//boardId로 이미지 조회
function findBoardImage() {
	var boardId = document.getElementById('boardDetailId').value;

	fetch(`/api/board/image/${boardId}`)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else if (response.status === 404) {
				console.log('해당하는 이미지가 없습니다.');
				return null;
			} else {
				throw new Error('이미지를 가져오는데 실패했습니다');
			}
		})
		.then(imageData => {
			if (imageData !== null) {
				const imageName = imageData.imageName;
				const imageElement = document.getElementById('boardDetailImage');
				const imageUrl = '/images/' + imageName;
				imageElement.src = imageUrl;
			}
		})
		.catch(error => {
			if (error.message !== '이미지를 가져오는데 실패했습니다') {
				console.error('Error : ', error);
			}
		});
}

//게시글 삭제
function deleteBoard() {
	var boardId = document.getElementById('boardDetailId').value;
	console.log(boardId);

	if (!confirm('정말 이 게시글을 삭제하시겠습니까?')) {
		return;
	}

	fetch(`/api/board/delete/${boardId}`, {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		}
	})
		.then(response => {
			if (response.ok) {
				alert('게시글이 성공적으로 삭제되었습니다.');
				document.getElementById('boardDetailPage').style.display = 'none';
				document.getElementById('boardListPage').style.display = 'block';
				location.reload();
			} else {
				throw new Error('게시글 삭제에 실패했습니다.');
			}
		})
		.catch(error => {
			console.error('Error:', error);
			alert('게시글 삭제 중 오류가 발생했습니다.');
		});
}

//게시글 수정
function updateBoard() {
	var boardId = document.getElementById('boardDetailId').value;
	var title = document.getElementById('boardUpdateTitle').value;
	var content = document.getElementById('boardUpdateContent').value;
	if (!confirm('게시글을 수정하시겠습니까?')) {
		return;
	}
	if (!title) {
		alert('제목을 입력해주세요');
		return;
	}
	if (!content) {
		alert('내용을 입력해주세요');
		return;
	}

	fetch(`/api/board/update/${boardId}`, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			boardTitle: title,
			boardContent: content
		})
	})
		.then(response => {
			if (response.ok) {
				alert('게시글 수정 성공');
				document.getElementById('boardEditPage').style.display = 'none';
				document.getElementById('boardListPage').style.display = 'block';
				location.reload();
			} else {
				throw new Error('게시글 수정 실패');
			}
		})
		.catch(error => {
			console.error('Error : ', error);
			alert('게시글 수정 실패');
		});

}

//자유게시판 리스트
function freeBoardByCategoryId() {
    const categoryId = 2;
    const url = `/api/board/category/${categoryId}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('게시글을 가져오는데 실패했습니다');
            }
            return response.json();
        })
        .then(boards => {
            const boardListContainer = document.querySelector('#boardFreeListPage ul');
            boardListContainer.innerHTML = '';

            const tableHeader = `
                <li class="board-item">
                    <span class="board-number-category">번호</span>
                    <span class="board-title-category">제목</span>
                    <span class="board-author-category">작성자</span>
                    <span class="board-good-category">추천</span>
                    <span class="board-readCount-category">조회수</span>
                </li>`;
            boardListContainer.insertAdjacentHTML('beforeend', tableHeader);

            processBoardsWithAuthors(boards, boardListContainer);
        })
        .catch(error => {
            console.error('게시글을 가져오는데 실패했습니다', error);
        });
}

function processBoardsWithAuthors(boards, boardListContainer) {
    const promises = boards.map((board, index) => {
        return findUserIdByBoardId(board.boardId)
            .then(userId => {
                return {
                    board,
                    userId
                };
            })
            .catch(error => {
                console.error(`게시글 ${board.boardId}의 작성자 정보를 가져오는데 실패했습니다`, error);
                return {
                    board,
                    userId: 'Unknown'
                };
            });
    });
    Promise.all(promises)
        .then(results => {
            results.forEach(({ board, userId }, index) => {
                const listItem = `
                    <li class="board-item">
                        <span class="board-number1">${index + 1}</span>
                        <input type="hidden" id="boardListContent" value="${board.boardContent}">
                        <a href="#" class="board-title1" onclick="goToBoardDetail(event)" 
                           data-board-id="${board.boardId}" 
                           data-board-title="${board.boardTitle}">
                           ${board.boardTitle}
                        </a>
                        <span class="board-author1">${userId}</span>
                        <span class="board-good1">${board.boardRecommend}</span>
                        <span class="board-readCount1">${board.boardReadCount}</span>
                    </li>`;
                boardListContainer.insertAdjacentHTML('beforeend', listItem);
            });

            document.getElementById('boardListPage').style.display = 'none';
            document.getElementById('boardFreeListPage').style.display = 'block';
        })
        .catch(error => {
            console.error('게시글 정보 표시 중 오류가 발생했습니다', error);
        });
}
//질문게시판 리스트
function questionBoardByCategoryId() {
	const categoryId = 3;
	fetch(`/api/board/category/${categoryId}`)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('게시글을 가져오는데 실패했습니다');
			}
		})
		.then(boards => {
			const boardListContainer = document.querySelector('#boardQuestionListPage ul');
			boardListContainer.innerHTML = ''
			const tableHeader = document.createElement('li');
			tableHeader.innerHTML = `
                <span class="board-number-category">번호</span>
                <span class="board-title-category">제목</span>
                <span class="board-author-category">작성자</span>
                <span class="board-good-category">추천</span>
                <span class="board-readCount-category">조회수</span>
            `;
			boardListContainer.appendChild(tableHeader);;

			boards.forEach((board, index) => {
				findUserIdByBoardId(board.boardId)
					.then(userId => {
						const listItem = document.createElement('li');
						listItem.innerHTML = `
                            <span class="board-number1">${index + 1}</span>
                            <input type="hidden" id="boardListContent" value="${board.boardContent}">
                            <a href="#" class="board-title1" onclick="goToBoardDetail(event)" 
                               data-board-id="${board.boardId}" 
                               data-board-title="${board.boardTitle}">
                               ${board.boardTitle}
                            </a>
                            <span class="board-author1">${userId}</span>
                            <span class="board-good1">${board.boardRecommend}</span>
                            <span class="board-readCount1">${board.boardReadCount}</span>
                        `;
						boardListContainer.appendChild(listItem);
					})
					.catch(error => {
						console.error('게시글 작성자 정보를 가져오는데 실패했습니다', error);
					});
			});

			document.getElementById('boardListPage').style.display = 'none';
			document.getElementById('boardQuestionListPage').style.display = 'block';
		})
		.catch(error => {
			console.error('게시글을 가져오는데 실패했습니다', error);
		});
}
//기타게시판 리스트
function restBoardByCategoryId() {
	const categoryId = 4;
	fetch(`/api/board/category/${categoryId}`)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('게시글을 가져오는데 실패했습니다');
			}
		})
		.then(boards => {
			const boardListContainer = document.querySelector('#boardRestListPage ul');
			boardListContainer.innerHTML = ''
			const tableHeader = document.createElement('li');
			tableHeader.innerHTML = `
                <span class="board-number-category">번호</span>
                <span class="board-title-category">제목</span>
                <span class="board-author-category">작성자</span>
                <span class="board-good-category">추천</span>
                <span class="board-readCount-category">조회수</span>
            `;
			boardListContainer.appendChild(tableHeader);;

			boards.forEach((board, index) => {
				findUserIdByBoardId(board.boardId)
					.then(userId => {
						const listItem = document.createElement('li');
						listItem.innerHTML = `
                            <span class="board-number1">${index + 1}</span>
                            <input type="hidden" id="boardListContent" value="${board.boardContent}">
                            <a href="#" class="board-title1" onclick="goToBoardDetail(event)" 
                               data-board-id="${board.boardId}" 
                               data-board-title="${board.boardTitle}">
                               ${board.boardTitle}
                            </a>
                            <span class="board-author1">${userId}</span>
                            <span class="board-good1">${board.boardRecommend}</span>
                            <span class="board-readCount1">${board.boardReadCount}</span>
                        `;
						boardListContainer.appendChild(listItem);
					})
					.catch(error => {
						console.error('게시글 작성자 정보를 가져오는데 실패했습니다', error);
					});
			});

			document.getElementById('boardListPage').style.display = 'none';
			document.getElementById('boardRestListPage').style.display = 'block';
		})
		.catch(error => {
			console.error('게시글을 가져오는데 실패했습니다', error);
		});
}

//검색
function searchBoard() {
	const searchInput = document.getElementById('searchInput').value.trim();

	if (!searchInput) {
		return;
	}
	fetch(`/api/board/searchByTitle?keyword=${searchInput}`)
		.then(response => {
			if (response.ok) {
				return response.json();
			} else {
				throw new Error('게시글 검색에 실패했습니다.');
			}
		})
		.then(boards => {
			const boardListContainer = document.querySelector('#boardListPage ul');
			boardListContainer.innerHTML = ''
			const tableHeader = document.createElement('li');
			tableHeader.innerHTML = `
                <span class="board-number-category">번호</span>
                <span class="board-title-category">제목</span>
                <span class="board-author-category">작성자</span>
                <span class="board-good-category">추천</span>
                <span class="board-readCount-category">조회수</span>
            `;
			boardListContainer.appendChild(tableHeader);;

			boards.forEach((board, index) => {
				const listItem = document.createElement('li');
				listItem.innerHTML = `
                    <span class="board-number">${index + 1}</span>
                    <input type="hidden" id="boardListContent" value="${board.boardContent}">
                    <a href="#" class="board-title" onclick="goToBoardDetail(event)" 
                       data-board-id="${board.boardId}" 
                       data-board-title="${board.boardTitle}">
                       ${board.boardTitle}
                    </a>
                    <span class="board-author"></span>
                    <span class="board-good">${board.boardRecommend}</span>
                    <span class="board-readCount">${board.boardReadCount}</span>
                `;
				boardListContainer.appendChild(listItem);
			});
		})
		.catch(error => {
			console.error('Error: ', error);
			alert('게시글 검색에 실패했습니다.');
		});
}

//조회수 증가
function increaseReadCount(boardId) {
	fetch(`/api/board/readCount/${boardId}`, {
		method: 'GET'
	})
		.then(response => {
			if (response.ok) {
				console.log('조회수 증가 성공')
			} else {
				console.log('조회수 증가 실패 : ', response.statusText);
			}
		})
		.catch(error => {
			console.error('Error : ', error);
		});

}


//게시글 추천
/*function recommend() {
	var boardId = document.getElementById('boardDetailId').value;
	var recommendCountElement = document.getElementById('boardDetailRecommend');
	var recommendCount = parseInt(recommendCountElement.innerText);

	findUserIdByBoardId(boardId)
		.then(userId => {
			const userIdElement = document.getElementById('detailUserId');
			const detailUserId = userIdElement ? userIdElement.value : null;

			if (detailUserId === userId) {
				alert('자신의 글은 추천할 수 없습니다');
				return;
			} else if (!detailUserId || detailUserId === null) {
				alert('회원만 추천할 수 있습니다');
				return;
			}
			var isRecommended = localStorage.getItem(`recommended_${boardId}`);
			var newRecommendCount = recommendCount;

			if (isRecommended === 'true') {
				newRecommendCount--;
				localStorage.removeItem(`recommended_${boardId}`);
			} else {
				newRecommendCount++;
				localStorage.setItem(`recommended_${boardId}`, 'true');
			}

			recommendCountElement.innerText = newRecommendCount;

			fetch(`/api/board/recommend/${boardId}?boardRecommend=${newRecommendCount}`, {
				method: 'PUT',
			})
				.then(response => {
					if (!response.ok) {
						throw new Error('추천 업데이트에 실패했습니다.');
					}
					return response.text();
				})
				.then(message => {
					console.log(message);
				})
				.catch(error => {
					console.error('Error:', error);
				});
		})
		.catch(error => {
			console.error('Error:', error);
		});
}*/
function recommend() {
	var userIdElement = document.getElementById('detailUserId');
	var userId = userIdElement ? userIdElement.value : null;
	var boardId = document.getElementById('boardDetailId').value;
	findUserIdByBoardId(boardId)
		.then(writerUserId => {
			if (userId === writerUserId) {
				alert('자신의 글은 추천할 수 없습니다');
				return;
			}
			if (userId === null || !userId) {
				alert('회원만 추천할 수 있습니다');
				return;
			}
			findRecommendCount(boardId)
				.then(recommendCount => {
					var recommendElement = document.getElementById('boardDetailRecommend');
					var recommendedKey = 'recommend_' + boardId + '_' + userId;
					var recommended = localStorage.getItem(recommendedKey);
					if (recommended === 'true') {
						recommendCount--;
						localStorage.removeItem(recommendedKey);
					} else {
						recommendCount++;
						localStorage.setItem(recommendedKey, 'true');
					}
					recommendElement.innerText = recommendCount;
					console.log(recommendElement);
					fetch(`/api/board/recommend/${boardId}?boardRecommend=${recommendCount}`, {
						method: 'PUT',
					})
						.then(response => {
							if (!response.ok) {
								throw new Error('추천 실패');
							} else {
								return response.text();
							}
						})
						.catch(error => {
							console.error('Error :', error);
						});
				})
		})
}
//추천수 찾기
function findRecommendCount(boardId) {
	return fetch(`/api/board/recommendCount/${boardId}`)
		.then(response => {
			if (response.ok) {
				return response.text();
			} else {
				throw new Error('추천수 정보를 가져오는데 실패했습니다.');
			}
		})
		.then(recommendCountStr => {
			const recommendCount = parseInt(recommendCountStr);
			if (!isNaN(recommendCount)) {
				return recommendCount;
			} else {
				throw new Error('유효하지 않은 추천수입니다.');
			}
		})
		.catch(error => {
			throw new Error('추천수 정보를 가져오는데 실패했습니다', error);
		});
}





//전체 게시글 정렬
function getBoardsBySorting() {
	const sorting = document.getElementById('sorting').value;
	if (sorting === '#') {
		return;
	}
	fetch(`/api/board/sorting?sorting=${sorting}`)
		.then(response => {
			if (!response.ok) {
				throw new Error('정렬실패');
			}
			return response.json();
		})
		.then(sortedBoards => {
			displaySortedBoardList(sortedBoards);
		})
		.catch(error => {
			console.error('Error:', error);
		});
}
function displaySortedBoardList(sortedBoards) {
	const boardList = $('#boardList');
	boardList.empty();

	const headerItem = `
        <li class = "board-item">
			<span class="board-category-category">분류</span>
			<span class="board-number-category">번호</span>
            <span class="board-title-category">제목</span>
            <span class="board-author-category">작성자</span>
            <span class="board-good-category">추천</span>
            <span class="board-readCount-category">조회수</span>
        </li>`;
	boardList.append(headerItem);
	sortedBoards.forEach(board => {
		Promise.all([
			findUserIdByBoardId(board.boardId),
			findBoardCategoryNameByBoardId(board.boardId)
		])
			.then(([userId, categoryName]) => {
				const listItem = `
                <li class = "board-item">
                    <span class="board-category-category">${categoryName}</span>
                    <span class="board-number">${board.boardId}</span>
                    <a href="#" class="board-title" onclick="goToBoardDetail(event)" data-board-id="${board.boardId}">${board.boardTitle}</a>
                    <span class="board-author">${userId}</span>
                    <span class="board-good">${board.boardRecommend}</span>
                    <span class="board-readCount">${board.boardReadCount}</span>
                </li>`;
				boardList.append(listItem);
			})
			.catch(error => {
				console.error('게시글 정보를 가져오는데 실패했습니다.', error);
				const listItem = `
                <li class = "board-item">
                    <span class="board-category-category">${categoryName}</span>
                    <span class="board-number">${board.boardId}</span>
                    <a href="#" class="board-title" onclick="goToBoardDetail(event)" data-board-id="${board.boardId}">${board.boardTitle}</a>
                    <span class="board-author">${userId}</span>
                    <span class="board-good">${board.boardRecommend}</span>
                    <span class="board-readCount">${board.boardReadCount}</span>
                </li>`;
				boardList.append(listItem);
			});
	});
}


//페이징
function fetchAndDisplayBoardList(page, size) {
	fetch(`/api/board/boards?page=${page}&size=${size}&_=${Date.now()}`)
		.then(response => {
			if (!response.ok) {
				throw new Error('게시글 목록을 가져오는데 실패했습니다.');
			}
			return response.json();
		})
		.then(data => {
			displayBoardList(data);
			updatePaginationUI(data);
		})
		.catch(error => {
			console.error('Error:', error);
			alert('게시글 목록을 가져오는데 실패했습니다.');
		});
}

// 게시글 목록을 화면 표시
async function displayBoardList(boardPage) {
    const boardListElement = $('#boardList');
    boardListElement.empty();

    const headerItem = `
        <li class="board-item">
            <span class="board-category-category">분류</span>
            <span class="board-number-category">번호</span>
            <span class="board-title-category">제목</span>
            <span class="board-author-category">작성자</span>
            <span class="board-good-category">추천</span>
            <span class="board-readCount-category">조회수</span>
        </li>`;
    boardListElement.append(headerItem);

    const boards = boardPage.content;

    for (const board of boards) {
        try {
            const [userId, categoryName] = await Promise.all([
                findUserIdByBoardId(board.boardId),
                findBoardCategoryNameByBoardId(board.boardId)
            ]);
            const listItem = `
                <li class="board-item">
                    <span class="board-category-category">${categoryName}</span>
                    <span class="board-number">${board.boardId}</span>
                    <a href="#" class="board-title" onclick="goToBoardDetail(event)" data-board-id="${board.boardId}">${board.boardTitle}</a>
                    <span class="board-author">${userId}</span>
                    <span class="board-good">${board.boardRecommend}</span>
                    <span class="board-readCount">${board.boardReadCount}</span>
                </li>`;
            boardListElement.append(listItem);
        } catch (error) {
            console.error('게시글 정보를 가져오는데 실패했습니다.', error);
        }
    }
    updatePaginationUI(boardPage);
}

// 페이징 UI를 업데이트
function updatePaginationUI(boardPage) {
	const paginationElement = document.getElementById('pagination');
	paginationElement.innerHTML = '';

	const totalPages = boardPage.totalPages;
	const currentPage = boardPage.number;

	for (let i = 0; i < totalPages; i++) {
		const pageNumber = i + 1;
		const pageButton = document.createElement('button');
		pageButton.textContent = pageNumber;
		pageButton.classList.add('pagination-button');
		pageButton.onclick = () => {
			fetchAndDisplayBoardList(i, boardPage.size);
		};

		if (i === currentPage) {
			pageButton.classList.add('active');
		}

		paginationElement.appendChild(pageButton);
	}
}

// 초기 페이지 로드 시 첫 번째 페이지의 게시글 목록 표시
document.addEventListener('DOMContentLoaded', () => {
	fetchAndDisplayBoardList(0, 5);
});


