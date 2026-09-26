# AI Powerlifting Coach API

An API that takes a video of a lift and returns AI feedback on the form.

This is API-only by design, no frontend. Talk to it with curl, Bruno, Postman, or whatever client you like.

## How it works

1. You video of a lift to the API.
2. A pose model (YOLO11 pose, run with Deep Java Library and PyTorch) finds the lifter's joints and their x/y positions. For video, FFmpeg splits it into frames first.
3. The joint positions are sent to a local LLM (Llama 3.1 through Ollama), which answers with feedback on the form.
<img width="1535" height="1025" alt="image" src="https://github.com/user-attachments/assets/7dfd43c2-9aea-44f9-bd3a-09dad6460c75" />

(this image was generated using AI) - Check out [Architecture Image](docs/architecture.md) for more details.

## Tech stack

- Kotlin + Spring Boot 3
- Deep Java Library (DJL) with PyTorch
- Ollama (Llama 3.1 8B)
- Apache Tika
- FFmpeg for splitting video into frames

## Requirements

- **Java 17 or newer**
- **Ollama** installed and running: https://ollama.com
- Internet access on first start (the pose model and PyTorch libraries are downloaded automatically)
- FFmpeg

## Run it

**1. Download the LLM model**

```bash
ollama pull llama3.1:8b
```

Make sure Ollama is running. It listens on `http://localhost:11434` by default.

**2. Install FFmpeg**

Windows:
```powershell
winget install -e --id Gyan.FFmpeg
```
macOS/Linux:

```bash
sudo apt update && sudo apt install ffmpeg -y
```

**3. Start the app**

macOS / Linux:
```bash
./gradlew bootRun
```

Windows:
```powershell
.\gradlew.bat bootRun
```

The app starts on `http://localhost:8080`. The first start takes a while because the pose model is downloaded.

By default the app uses the `local` profile, which needs no database.

```bash
SPRING_PROFILES_ACTIVE=docker ./gradlew bootRun
```

Windows (PowerShell):
```powershell
$env:SPRING_PROFILES_ACTIVE="docker"; .\gradlew.bat bootRun
```

## Use it

Send a `POST` request to `/coach` as `multipart/form-data` with two fields:

| Field | Value |
|---|---|
| `file` | An MP4 or QuickTime/MOV video |
| `exercise` | Free text, e.g. `squat` (not yet used to tailor the feedback) |

With curl:
```bash
curl -X POST http://localhost:8080/coach -F "file=@squat.mp4" -F "exercise=squat"
```

With Bruno or Postman: choose **Multipart Form**, add the key `file` with your video as the value, and the key `exercise` with any text.

### Swagger docs

Interactive API docs are served at `http://localhost:8080/swagger-ui.html` once the app is running.

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
  "message": "Invalid file extension: application/pdf, the allowed extensions are: [video/mp4, video/quicktime]"
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
- **415 Unsupported Media Type**: only MP4 and QuickTime/MOV videos are accepted.
- **400 Bad Request**: both `file` and `exercise` form fields are required.
- **Slow first start**: normal, the pose model is being downloaded.

# For educational purposes only

This project is built by Marius Cook as a learning project.

It was written with AI assistance. The idea, the architecture and the decisions behind it are mine, as is much of the code. AI was used as a tool along the way: looking things up in poorly documented libraries, working through bugs, and writing parts I chose to hand off.
