package tes.dev.waste_microservice.exception;


public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String field;
    private Object value;

    public ResourceNotFoundException(String resourceName, String field, Object value) {
        super(String.format("Not found %s  with: %s = '%s' ", resourceName, field, value));
        this.resourceName = resourceName;
        this.field = field;
        this.value = value;
    }

}
