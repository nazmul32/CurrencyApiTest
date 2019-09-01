import com.google.gson.annotations.SerializedName
import java.util.concurrent.ConcurrentHashMap

data class ApiResponse (
	@SerializedName("base") val base : String,
	@SerializedName("date") val date : String,
	@SerializedName("rates") val rates : ConcurrentHashMap<String, Double>
)