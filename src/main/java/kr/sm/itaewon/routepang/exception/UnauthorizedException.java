package kr.sm.itaewon.routepang.exception;

public class UnauthorizedException extends RuntimeException{
    private static final long serialVersionUID = -2238030302650813813L;

    public UnauthorizedException() {
        super("로그인 정보가 유효하지 않습니다. 다시 로그인을 해주세요.");
    }
}