# git permission denied

```sh
git --no-optional-locks -c color.branch=false -c color.diff=false -c color.status=false -c diff.mnemonicprefix=false -c core.quotepath=false -c credential.helper=sourcetree push -v --tags origin refs/heads/feature/GPP-7460/BEST-100/sslee_sub:refs/heads/feature/GPP-7460/BEST-100/sslee_sub 
Pushing to [보안상 가림]:[보안상 가림].git
ERROR: Permission to [보안상 가림].git denied to sangsulee.
fatal: Could not read from remote repository.

Please make sure you have the correct access rights
and the repository exists.
Completed with errors, see above
```

1. 터미널: `$ ssh-keygen -t rsa -C "git이메일을 여기에 써주세요."`

```
Generating public/private rsa key pair.
Enter file in which to save the key (/Users/sangsulee/.ssh/id_rsa): 
Enter passphrase (empty for no passphrase): 
Enter same passphrase again: 
Your identification has been saved in /Users/sangsulee/.ssh/id_rsa.
Your public key has been saved in /Users/sangsulee/.ssh/id_rsa.pub.
The key fingerprint is:
SHA256:[보안상 가림]
The key's randomart image is:
+---[RSA 3072]----+
|        +*=oooX%X|
|        .*.=+oB%B|
|        + *..O+B+|
|       o o .o BE.|
|      . S    .   |
|                 |
|                 |
|                 |
|                 |
+----[SHA256]-----+
```

2. 키 복사

```
LM-SEL-16517446:~ sangsulee$ cat ~/.ssh/id_rsa.pub
[보안상 가림]
```

3. ssh-rsa 부터 다 복사. 이메일 포함 까지 복사

...

git 서버 작업 중으로 생긴 문제였다....

ssh 새로 만들어도 root 권한 쓰기가 주어지지 않으면 push 불가