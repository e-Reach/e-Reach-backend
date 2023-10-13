package org.ereach.inc.utilities;

public class Constants {
	
	public static final String E_REACH_USERNAME_PREFIX = "e-Reach-";
	public static final String SHA_256_ALGORITHM = "SHA-256";
	public static final String HOSPITAL = "hospital";
	public static final String NO_PRACTITIONER_FOUND = "No Practitioner(s) found";
	
	public static final String ACCOUNT_ACTIVATION_SUCCESSFUL = "Account Activation Successful";
	public static final String PRACTITIONER = "practitioner";
	public static final String PATIENT_WITH_PIN_DOES_NOT_EXIST = "patient with pin %s does not exist";
	public static final String PATIENT_WITH_ID_DOES_NOT_EXIST = "patient with id %s does not exist";
	public static final String SHA_256_ALGORITHM_NOT_AVAILABLE = "SHA-256 algorithm not available.";
	public static final String TOKEN_WAS_INVALID = "Request failed: %s failed to save permanently, token was invalid";
	public static final String SPACE = " ";
	public static final String BACKEND_BASE_URL = "http://localhost:8080/api/v1/";
	public static final String FRONTEND_LOCALHOST_BASE_URL = "http://localhost:3000/";
	public static final String FRONTEND_VERCEL_BASE_URL = "https://e-reach.vercel.app/";
	public static final String ACTIVATE_HOSPITAL_ACCOUNT = "https://e-reach.vercel.app/activate-hospital-account";
	public static final String ACTIVATE_HOSPITAL_ADMIN_ACCOUNT = "https://e-reach.vercel.app/activate-hospital-admin-account";
	public static final String FRONT_END_ACTIVATE_ACCOUNT = "https://e-reach.vercel.app/activate-account";
	
	public static final String QUERY_STRING_PREFIX = "/";
	
	public static final String HOSPITAL_WITH_EMAIL_DOES_NOT_EXIST = "Hospital with email %s does not exist";
	public static final String HOSPITAL_WITH_ID_DOES_NOT_EXIST = "Hospital with id %s does not exist";
	public static final String ADMIN_WITH_ID_DOES_NOT_EXIST_IN_HOSPITAL = "Admin with id %s does not exist in the hospital with email %s";
	public static final String ADMIN_WITH_ID_DOES_NOT_EXIST = "Admin with id %s does not exist";
	public static final String ADMIN_WITH_EMAIL_DOES_NOT_EXIST_IN_HOSPITAL = "Admin with email %s does not exist in the hospital with email %s";
	public static final String ADMIN_WITH_EMAIL_DOES_NOT_EXIST = "Admin with email %s does not exist";
	public static final String ACTIVATE_ACCOUNT = "practitioner/activate-account/";
	public static final String QUERY_STRING_TOKEN = "token=";
	public static final String USER_ROLE = "user password";
	public static final String USER_PHONE_NUMBER = "user phone number";
	public static final String CLAIMS = "claims";
	public static final String LIBRARY_ISSUER_NAME = "e-Reach Incorporation";
	public static final String USER_MAIL = "user mail";
	public static final String FIRST_NAME = "first name";
	public static final String LAST_NAME = "last name";
	public static final String TEST_HOSPITAL_NAME = "test hospital name";
	public static final String TEST_HOSPITAL_MAIL = "test hospital mail";
	public static final String PATIENT_APPOINTMENT_MAIL_PATH = "C:\\Users\\PC\\IdeaProjects\\e-Reach-backend\\src\\main\\resources\\templates\\patient_appointment_mail_template.html";
	public static final String ACTIVATION_MAIL_PATH = "classpath:/templates/account_activation_mail_template.html";
	public static final String HOSPITAL_ACCOUNT_ACTIVATION_MAIL_PATH = "classpath:/templates/hospital_account_activation_template.html";
	public static final String PRACTITIONER_ACCOUNT_ACTIVATION_MAIL_PATH = "classpath:/templates/practitioner_url_template.html";
	public static final String PATIENT_ID_MAIL_PATH = "classpath:/templates/patient_id_template.html";
	public static final String CONSTRAINT_VIOLATION_MESSAGE =
                                """
								Invalid email:: criteria for a valid email includes the following
								1.) email must not contain any white space
								2.) email domain must be a valid domain and valid domain includes %s
								3.) email must be a valid email syntax, like contain an '@', which separate\
								4.) the domain from the user name
								""";
	public static final String SENDER_FULL_NAME = "e-Reach inc";
	public static final String SENDER_EMAIL = "noreply@ereachtech.com";
	public static final String API_KEY = "api-key";
	public static final String INVALID_PATIENT_IDENTIFICATION_NUMBER = "Invalid patient identification number";
	public static final String INVALID_HOSPITAL_EMAIL = "Invalid hospital email";
	public static final String BREVO_SEND_EMAIL_API_URL = "https://api.brevo.com/v3/smtp/email";
	public static final String MESSAGE_SUCCESSFULLY_SENT = "Message Sent Successfully";
	public static final String MESSAGE_FAILED_TO_SEND = "Message Could Not Be Sent";
	public static final String MESSAGE_SUBJECT = "Support At e-Reach inc";
	public static final String TEMPLATE_LOAD_FAILED = "Error loading template content Template failed to load:: ";
	public static  final String FIELD_INVALID_EXCEPTIONS = "Phone number must not contain alphabet";
	public  static final String INCOMPLETE_DETAILS_PROVIDED = "Incomplete details provided";
	public static  final  String MEDICATION_ADDED_SUCCESSFULLY = "Added Successfully";
	public static final String RECORD_WITH_P_I_N_NOT_FOUND = "Record with p.i.n %s not found";
	
	public static  final  String PHARMACIST_SUCCESSFULLY_CREATED = "\uD83E\uDD13\uD83E\uDD13\uD83E\uDD13\uD83E\uDD13\uD83E\uDD13\uD83E\uDD13 successfully created";
	public static  final  String EMAIL_ALREADY_EXIST = "Email already exists";
	public static final String PATIENT_ACCOUNT_CREATED_SUCCESSFULLY = "Patient %s Account Created Successfully, you will receive a mail with your P.I.N and Username very soon which";
	public static final String DOCTOR_ACCOUNT_CREATED_SUCCESSFULLY = "Doctor %s Account Created Successfully";
	public static final String PRACTITIONER_REGISTRATION_AWAITING_CONFIRMATION = "Doctor %s Account awaiting confirmation";
	public static final String NO_MEDICAL_LOGS_FOUND_FOR_PATIENT = "No medical log(s) found for patient with P.I.N %s";
	public static final String NO_MEDICAL_LOGS_FOUND_FOR_PATIENT_WITH_DATE = "No medical log(s) found for patient with P.I.N %s on this day: %s";
}
