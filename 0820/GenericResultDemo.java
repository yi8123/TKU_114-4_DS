public class GenericResultDemo {

    public static class Result<T> {
        private final boolean success;
        private final String message;
        private final T data;

        private Result(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public static <T> Result<T> ok(T data) {
            return new Result<>(true, "Success", data);
        }

        public static <T> Result<T> fail(String message) {
            return new Result<>(false, message, null);
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public T getData() { return data; }

        @Override
        public String toString() {
            return "Result{success=" + success + ", message='" + message + "', data=" + data + "}";
        }
    }

    public static void main(String[] args) {
        // Result<String>
        Result<String> strSuccess = Result.ok("Hello Generic");
        Result<String> strFail = Result.fail("User not found");
        String sVal = strSuccess.getData(); // 不需要 cast
        System.out.println("String Result (Success): " + sVal);
        System.out.println("String Result (Fail): " + strFail);

        // Result<Integer>
        Result<Integer> intSuccess = Result.ok(200);
        Result<Integer> intFail = Result.fail("Calculation error");
        Integer iVal = intSuccess.getData(); // 不需要 cast
        System.out.println("Integer Result (Success): " + iVal);
        System.out.println("Integer Result (Fail): " + intFail);
    }
}