const usernameValueMinLength = 4;
const usernameValueMaxLength = 16;
const passwordValueMinLength = 4;
const passwordValueMaxLength = 16;
const emailRegExp = new RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);

function LoginForm__submit(form) {
    form.username.value = form.username.value.trim();
    form.password.value = form.password.value.trim();

    if (form.username.value.length < usernameValueMinLength || usernameValueMaxLength < form.username.value.length) {
        toastWarning('아이디를 확인해주세요.');
        form.username.focus();
        return false;
    }

    if (form.password.value.length < passwordValueMinLength || passwordValueMaxLength < form.password.value.length) {
        toastWarning('비밀번호를 확인해주세요.');
        form.password.focus();
        return false;
    }

    form.submit();
}

function JoinForm__submit(form) {
    form.username.value = form.username.value.trim();
    form.password.value = form.password.value.trim();
    form.passwordValidation.value = form.passwordValidation.value.trim();
    form.email.value = form.email.value.trim();

    if (form.username.value.length < usernameValueMinLength || usernameValueMaxLength < form.username.value.length) {
        toastWarning('아이디는 공백 없이 4자 이상 16자 이하로 작성해야 합니다.');
        form.username.focus();
        return false;
    }

    if (form.password.value.length < passwordValueMinLength || passwordValueMaxLength < form.password.value.length) {
        toastWarning('비밀번호는 공백 없이 4자 이상 16자 이하로 작성해야 합니다.');
        form.password.focus();
        return false;
    }

    if (form.password.value !== form.passwordValidation.value) {
        toastWarning('동일한 비밀번호를 입력해주세요.');
        form.password.focus();
        return false;
    }

    if (!emailRegExp.test(form.email.value)) {
        toastWarning('이메일 형식에 맞춰 다시 입력해주세요.');
        form.email.focus();
        return false;
    }

    if (!form.personalInfoAgreement.checked) {
        toastWarning('개인정보 수집 및 활용 약관에 동의해주세요.')
        form.personalInfoAgreement.focus();
        return false;
    }

    form.submit();
}

function ModifyForm__submit(form) {
    form.password.value = form.password.value.trim();
    form.passwordValidation.value = form.passwordValidation.value.trim();
    form.email.value = form.email.value.trim();
    form.username.value = form.username.value.trim();

    if (form.password.value !== form.passwordValidation.value) {
        toastWarning('동일한 비밀번호를 입력해주세요');
        form.password.focus();
        return false;
    }

    if (form.password.value.length < passwordValueMinLength || passwordValueMaxLength < form.password.value.length) {
        toastWarning('비밀번호는 공백 없이 4자 이상 16자 이하로 작성해야 합니다.');
        form.password.focus();
        return false;
    }

    if (!emailRegExp.test(form.email.value)) {
        toastWarning('이메일 형식에 맞춰 다시 입력해주세요.');
        form.email.focus();
        return false;
    }

    if (form.password.value == null) {
        toastWarning('비밀번호를 입력해주세요.');
        form.password.focus();
        return false;
    }

    if (form.file.files.length > 0) {
        const file = form.file.files[0];
        const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;
        if (!allowedExtensions.exec(file.name)) {
            toastWarning('지원되는 파일 형식은 JPG, JPEG, PNG입니다.');
            return false;
        }
        const fileSizeLimit = 5 * 1024 * 1024; // 5MB
        if (file.size > fileSizeLimit) {
            toastWarning('파일 크기는 최대 5MB를 초과할 수 없습니다.');
            return false;
        }
    }

    form.submit();
}


function Withdraw__Submit(form) {
    let isConfirm = confirm('탈퇴한 회원은 복구할 수 없습니다.\n그래도 탈퇴하시겠습니까?');
    if (isConfirm === true) {
        let input = prompt('비밀번호를 입력해주세요.');
        if (input != null && passwordValueMinLength <= input.trim().length && input.trim().length <= passwordValueMaxLength) {
            let header = $("meta[name='_csrf_header']").attr('content');
            let token = $("meta[name='_csrf']").attr('content');

            $.ajax({
                url: "/usr/member/withdraw",
                type: "POST",
                data: {
                    password: input
                },
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (result) {
                    toastNotice('탈퇴 완료');
                    window.location.href = '/';
                },
                error: function (request, status, error) {
                    toastWarning('올바르지 않은 비밀번호입니다.');
                }
            })
        } else if (passwordValueMinLength > input.trim().length || passwordValueMaxLength < input.trim().length)
            toastWarning('올바르지 않은 비밀번호입니다.');
        else {
            toastNotice('취소되었습니다.');
        }
    }
}

function FindUsernameForm__submit(form) {
    form.email.value = form.email.value.trim();

    if (!emailRegExp.test(form.email.value)) {
        toastWarning('이메일 형식을 지켜야 합니다.');
        form.email.focus();
        return false;
    }

    debugger;
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');

    $.ajax({
        url: form.action,
        type: "POST",
        data: {
            _csrf: form._csrf.value,
            email: form.email.value
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (result) {
            toastNotice(result);
        },
        error: function (request, status, error) {
            toastNotice(error);
        }
    })
}

function FindPasswordForm__submit(form) {
    form.email.value = form.email.value.trim();
    form.username.value = form.username.value.trim();

    if (!emailRegExp.test(form.email.value)) {
        toastWarning('이메일 형식을 지켜야 합니다.');
        form.email.focus();
        return false;
    }

    if (form.username.value.length < usernameValueMinLength || usernameValueMaxLength < form.username.value.length) {
        toastWarning('아이디는 공백 없이 4자 이상 16자 이하로 작성해야 합니다.');
        form.username.focus();
        return false;
    }

    debugger;
    let header = $("meta[name='_csrf_header']").attr('content');
    let token = $("meta[name='_csrf']").attr('content');

    $.ajax({
        url: form.action,
        type: "POST",
        data: {
            _csrf: form._csrf.value,
            email: form.email.value,
            username: form.username.value
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (result) {
            alert(result);
        },
        error: function (request, status, error) {
            alert(error);
        }
    })
}