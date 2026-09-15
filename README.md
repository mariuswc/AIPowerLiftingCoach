# AI Powerlifting Coach

Upload a photo of yourself lifting and get feedback on your form.

## How it works

1. You send a photo (JPEG or PNG) to the API.
2. The app checks that the file really is an image, using Apache Tika.
3. A pose model (YOLO11 pose, run with Deep Java Library and PyTorch) finds your joints and their x/y positions.
4. The joint positions are sent to a local LLM (Llama 3.1 through Ollama), which answers with feedback on your form.

## Tech stack

- Kotlin + Spring Boot 3
- Deep Java Library (DJL) with PyTorch
- Ollama (Llama 3.1 8B)
- Apache Tika


## Requirements

- **Java 17 or newer**
- **Ollama** installed and running: https://ollama.com
- Internet access on first start (the pose model and PyTorch libraries are downloaded automatically)


## Run it

**1. Download the LLM model**

```bash
ollama pull llama3.1:8b
```

Make sure Ollama is running. It listens on `http://localhost:11434` by default.

**2. Start the app**

macOS / Linux:
```bash
./gradlew bootRun
```

Windows:
```powershell
.\gradlew.bat bootRun
```

The app starts on `http://localhost:8080`. The first start takes a while because the pose model is downloaded.

By default the app uses the `local` profile


```bash
SPRING_PROFILES_ACTIVE=docker ./gradlew bootRun
```

Windows (PowerShell):
```powershell
$env:SPRING_PROFILES_ACTIVE="docker"; .\gradlew.bat bootRun
```

## Use it

Send a `POST` request to `/coach` as `multipart/form-data`, with the image in a field named `file`.

With curl:
```bash
curl -X POST http://localhost:8080/coach -F "file=@squat.jpg"
```

With Bruno or Postman: choose **Multipart Form**, add the key `file`, and pick your image as the value.

**Successful response (200):**
```json
{
  "response": "Your knees are caving in slightly at the bottom of the squat..."
}
```

**Wrong file type (415):**
```json
{
  "errorCode": "INVALID_FILE_EXTENSION",
  "message": "The uploaded file contains an invalid extension: application/pdf"
}
```

## Configuration

Settings are in `src/main/resources/application.yaml`:

| Setting | Default | What it does |
|---|---|---|
| `server.port` | `8080` | Port the app runs on |
| `spring.ai.ollama.base-url` | `http://localhost:11434` | Where Ollama is running |
| `spring.servlet.multipart.max-file-size` | `500MB` | Max upload size |

## Troubleshooting

- **Connection refused when calling `/coach`**: Ollama is not running. Start it and try again.
- **415 Unsupported Media Type**: only JPEG and PNG are accepted.
- **400 Bad Request**: the form field must be named `file`.
- **Slow first start**: normal, the pose model is being downloaded.



# For education purposes only
This project is fully developed by Marius Cook - this is a project for educational purposes only. This project fully written by me and not AI.