# AWS Notes

---

## 1 — Learning Path Overview

A complete path to master AWS and integrate it with Spring Boot, covering core services like EC2, S3, RDS, Lambda, CodePipeline, and more — ending with a production-ready microservice on AWS.

**What's covered in this series:**

- Upload files to AWS S3 with Spring Boot
- Deploy Java apps on EC2 & Elastic Beanstalk
- Use RDS for database integration
- Configure CloudWatch for logs
- Background processing with SQS
- CI/CD pipeline with CodePipeline & CodeDeploy
- Infrastructure management using Terraform
- Final project: Build & deploy a production-ready microservice on AWS

---

## 2 — AWS Free Tier Account Setup

### 2.1 — Create a New AWS Free Account

1. Go to [aws.amazon.com](https://aws.amazon.com) and click **Create a Free Account**
2. Enter your email address and choose an AWS account name
3. Set a strong root password
4. Enter payment details (required, but free tier won't charge you)
5. Complete phone verification
6. Choose **Basic Support (Free)**
7. Wait for the activation confirmation email — your account isn't ready until it arrives

> ⚠️ Use the root account only for initial setup. Create an IAM user for all daily activity.

### 2.2 — AWS Console Quick Tour

| Area | What it is |
|---|---|
| **Services menu** | Top-left — access all AWS services |
| **Search bar** | Fastest way to navigate to any service |
| **Region selector** | Top-right — always check you're in the right region |
| **Account menu** | Top-right — billing, IAM, sign out |
| **CloudShell** | Browser-based terminal for running AWS CLI commands |

> ⚠️ Resources are region-specific. An EC2 instance created in `us-east-1` is invisible if you switch to `ap-south-1`.

### 2.3 — Set Up a Budget Alert

Prevent surprise charges by configuring a billing alert before doing anything else.

1. Search for **Billing and Cost Management** in the console search bar
2. From the sidebar, go to **Budgets**
3. Click **Create budget**
4. Choose **Use a template** → **Zero spend budget** (alerts you the moment any charge occurs)
5. Add your email in **Email recipients**
6. Click **Create budget**

The created budget appears in the list showing **$0.00 used**. Click the budget name to see a breakdown of usage and forecasted spend.

### 2.4 — Common Mistakes to Avoid

| Mistake | Fix |
|---|---|
| **Skipping billing alerts** | Always set up a cost budget with email alerts before anything else |
| **Using large EC2 or RDS instances** | Stick to `t2.micro` or other free-tier-eligible types only |
| **Deploying from the root user** | Create an IAM user with limited permissions for daily activity |
| **Forgetting to stop/terminate resources** | EC2, RDS, and EBS volumes all incur charges if left running |
| **Storing large files in S3 without cleanup** | Free tier includes 5 GB — monitor S3 usage regularly |
| **Missing the activation email** | Your account isn't ready until AWS sends a confirmation email |
| **Choosing a paid support plan** | Select **Basic Support (Free)** during sign-up |
| **Not monitoring usage** | Check the **Billing Dashboard** monthly to track spend |

---

## 3 — IAM (Identity and Access Management)

IAM is the foundation of AWS security — it controls *who* can access *what* in your AWS account.

| Concept | What it is |
|---|---|
| **User** | A person or app with long-term credentials |
| **Group** | A collection of users that share the same permissions |
| **Policy** | A JSON document that defines allowed/denied actions |
| **Role** | A set of permissions that can be temporarily assumed by a user or service |

### 3.1 — Create an IAM User

1. Search **IAM** in the console → **IAM Dashboard** → sidebar → **Users** → **Create user**
2. Enter a username (e.g. `Utkarsh`)
3. Check **Provide user access to the AWS Management Console**
4. Set a password → **Create user**

A sign-in link is generated for the new user. Open it in incognito and log in — you'll see the logged-in name is different from the root account (`LearningAccount`).

> At this point the user has no permissions. Trying to open EC2 will show API errors.

### 3.2 — Attach Policies to a User

1. From the user list, click the user → **Add permissions** → **Attach policies directly**
2. Search `ec2` → tick **AmazonEC2FullAccess**
3. Search `s3` → tick **AmazonS3FullAccess**
4. Save

Refresh the IAM user's session — all EC2 errors are gone. The user can now also create S3 buckets, which are visible from the root account too.

**What a policy looks like (S3 Full Access):**

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "s3:*",
        "s3-object-lambda:*"
      ],
      "Resource": "*"
    }
  ]
}
```

### 3.3 — Groups

Instead of attaching policies to each user individually, put users in a group and attach policies to the group.

1. Sidebar → **User groups** → **Create group**
2. Name it (e.g. `developer`)
3. Attach policies (EC2, Lambda, S3)
4. **Create user group**

When creating new users, add them to this group during creation — they automatically inherit all group permissions.

### 3.4 — Roles

> A **Role** is a set of permissions that anyone (or anything) can *temporarily assume* to do a specific task — unlike a user, it has no long-term password or access key.

**Create a role:**

1. Sidebar → **Roles** → **Create role**
2. Trusted entity type → **AWS account**
3. Attach policies (e.g. Billing and Cost Management)
4. Name it (e.g. `billing-role`) → **Create**

**Assign the role to a user:**

The user needs permission to *assume* the role. Go to the user → **Add permissions** → **Create inline policy**:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "Statement1",
      "Effect": "Allow",
      "Action": "sts:AssumeRole",
      "Resource": "arn:aws:iam::183088117435:role/billing-role"
    }
  ]
}
```

Give the policy a name → **Create**. The policy is now attached to the user.

**Assume the role:**

Simply having the `sts:AssumeRole` permission isn't enough — the user must actively switch into the role:

1. Open the created role → copy the **Link to switch roles in console**
2. Open the link in the browser → click **Switch Role**
3. The console header now shows the role name instead of the user name
4. Navigate to Billing and Cost Management — no errors ✅

Switch back to your user at any time from the account menu.

### 3.5 — Programmatic Access (Access Keys)

For CLI or SDK access (instead of console password):

1. Go to the user → **Security credentials** → **Create access key**
2. Choose use case (CLI, SDK, etc.)
3. Download the `.csv` — you won't be able to see the secret key again

> ⚠️ Never commit access keys to Git. Use environment variables or AWS credential files.

### IAM Key Takeaways

| Concept | Rule of thumb |
|---|---|
| Root user | Only for account setup — never use day-to-day |
| Users | One per person; attach policies via groups, not directly |
| Groups | Assign permissions at the group level for easier management |
| Policies | JSON documents — `Allow` or `Deny` on specific `Action` + `Resource` |
| Roles | Use for temporary access or service-to-service permissions |
| `sts:AssumeRole` | Required on the user to let them switch into a role |

---

## 4 — S3 (Simple Storage Service)

S3 is AWS's object storage service — store and retrieve any amount of data from anywhere. Think of it as an infinitely scalable hard drive in the cloud.

```
            +--------+
            |  S3    |
            +--------+
                |
                v
          +------------+
          |   Bucket   |   <-- container / folder
          +------------+
           /     |     \
          v      v      v
   +---------+ +---------+ +---------+
   |Object 1 | |Object 2 | |Object 3 |
   +---------+ +---------+ +---------+
```

### 4.1 — Key Concepts

| Term | What it is |
|---|---|
| **Bucket** | Top-level container for your files. Name must be globally unique across all AWS accounts |
| **Object** | Any file you upload, stored with metadata and a unique key |
| **Key** | The unique name/path for an object inside a bucket (e.g. `images/photo.jpg`) |
| **Region** | The AWS region where your bucket physically resides |
| **Storage Class** | Cost/durability trade-off options — Standard, Intelligent-Tiering, Glacier, etc. |
| **Public Access** | Controls whether the bucket or individual objects are accessible to the internet |

**Storage classes:**

| Class | Best for |
|---|---|
| **Standard** | Frequently accessed data |
| **Intelligent-Tiering** | Unknown or changing access patterns — auto-moves between tiers |
| **Standard-IA** | Infrequently accessed but needs fast retrieval |
| **Glacier** | Long-term archival — retrieval takes minutes to hours |
| **Glacier Deep Archive** | Cheapest option — retrieval takes up to 12 hours |

> ⚠️ Free tier includes **5 GB** of S3 Standard storage. Monitor usage to avoid charges.

### 4.2 — Step-by-step Bucket Creation

1. Search **S3** in the console → click **Create bucket**
2. **Bucket name** — must be globally unique (e.g. `utk-my-first-bucket-2026`)
3. **Region** — pick the region closest to you
4. **Object Ownership** — leave as default (ACLs disabled) — prevents other accounts from owning uploaded objects
5. **Block Public Access** — leave all boxes checked for private buckets (blocks internet access)
6. **Versioning** — enable to keep previous versions of files (useful for backups)
7. **Encryption** — leave as default (SSE-S3)
8. **Advanced options** → Object Lock automatically enables versioning if turned on
9. Click **Create bucket**

The bucket appears in the S3 bucket list. Click into it to upload files.

**Upload an object:**

1. Open the bucket → click **Upload**
2. Click **Add files** → select a file
3. Leave all settings as default → click **Upload**
4. The file appears in the bucket with its key (filename), size, and storage class

**Access an object:**

- Click the object → **Object URL** — this is the direct link to the file
- By default it returns `Access Denied` because public access is blocked
- To make a single file public: **Object actions** → **Make public** (only works if bucket-level public access is unblocked first)

> ⚠️ Never make a bucket fully public unless serving static files. Use **pre-signed URLs** instead to share individual files temporarily without changing permissions.

---

## 5 — Spring Boot + AWS S3

Integrate a Spring Boot app with S3 to upload and download files using the AWS SDK v2.

```
+------------------------------------------------------------------+
|                                                                  |
|  Local Application                                               |
|                                                                  |
|   +--------------+      +-------------+      +-------------+     |
|   |              | ---> |             | ---> |             |     |
|   |  Spring App  |      |   AWS SDK   |      |  S3 Client  |     |
|   |              |      |             |      |             |     |
|   +--------------+      +-------------+      +-------------+     |
|                                                                  |
+------------------------------------------------------------------+
```

### 5.1 — Project Setup

Create a new Spring Boot project from [start.spring.io](https://start.spring.io):

```
groupId:    com.utkarsh
artifactId: S3DemoApplication
dependency: Spring Web
```

Add the AWS SDK S3 dependency to [`pom.xml`](pom.xml):

```xml
<dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>s3</artifactId>
    <version>2.42.38</version>
</dependency>
```

### 5.2 — Project Structure

```
src/main/java/com/utkarsh/s3demoapplication/
├── S3DemoApplication.java
├── config/
│   └── S3Config.java
├── controller/
│   └── S3Controller.java
└── service/
    └── S3Service.java
src/main/resources/
└── application.properties
```

### 5.3 — Configuration

[`application.properties`](src/main/resources/application.properties):

```properties
cloud.aws.credentials.access-key=YOUR_ACCESS_KEY
cloud.aws.credentials.secret-key=YOUR_SECRET_KEY
cloud.aws.region.static=ap-south-1

aws.bucket.name=YOUR_BUCKET_NAME
```

> ⚠️ Never commit real credentials to Git. Use environment variables or AWS credential files in production.

[`config/S3Config.java`](src/main/java/com/utkarsh/s3demoapplication/config/S3Config.java) — builds and exposes an `S3Client` bean:

```java
@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }
}
```

### 5.4 — Service Layer

[`service/S3Service.java`](src/main/java/com/utkarsh/s3demoapplication/service/S3Service.java):

```java
@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;

    public void uploadFile(MultipartFile file) throws IOException {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(file.getOriginalFilename())
                        .build(),
                RequestBody.fromBytes(file.getBytes()));
    }

    public byte[] downloadFile(String key) {
        ResponseBytes<GetObjectResponse> objectAsBytes = s3Client.getObjectAsBytes(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build());
        return objectAsBytes.asByteArray();
    }
}
```

### 5.5 — Controller

[`controller/S3Controller.java`](src/main/java/com/utkarsh/s3demoapplication/controller/S3Controller.java):

```java
@RestController
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        s3Service.uploadFile(file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable String fileName) {
        byte[] data = s3Service.downloadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(data);
    }
}
```

### 5.6 — Generate Access Keys

1. AWS Console → **IAM** → **Users** → **Create user** (e.g. `s3-application-local`)
2. Attach **AmazonS3FullAccess** policy
3. Go to user → **Security credentials** → **Create access key** → choose **Local code**
4. Copy the **Access Key ID** and **Secret Access Key** into `application.properties`
5. Go to **S3** → copy your bucket name and region (e.g. `ap-south-1`) into `application.properties`

### 5.7 — Test with Postman

**Upload:**

```
POST  http://localhost:8080/upload
Body  → form-data → Key: file (type: File) → select a file
```

Response: `File uploaded successfully` ✅

**Download:**

```
GET  http://localhost:8080/download/java_devops_learning_roadmap.html
```

The file downloads as an attachment ✅. Verify the uploaded file also appears in your S3 bucket in the AWS console.

### Key Takeaways

| Concept | Note |
|---|---|
| `S3Client` | The main AWS SDK object for all S3 operations — configure once as a `@Bean` |
| `PutObjectRequest` | Used to upload — specify bucket name and key (filename) |
| `GetObjectRequest` | Used to download — returns bytes you can stream back to the client |
| Object key | The filename/path used to identify the object inside the bucket |
| `Content-Disposition` header | Tells the browser to download the file rather than display it |
| Access keys | Create a dedicated IAM user per app — never use root credentials |

---

## 6 — EC2 (Elastic Compute Cloud)

EC2 provides virtual servers (instances) in the cloud — you choose the OS, hardware specs, networking, and storage, then run whatever you want on it.

```
  Your Machine
       │
       │  SSH (port 22)
       ▼
  ┌─────────────────────────────┐
  │        EC2 Instance         │
  │  ┌──────────────────────┐   │
  │  │   Amazon Linux 2023  │   │
  │  │   Apache / App / ... │   │
  │  └──────────────────────┘   │
  │  Security Group (Firewall)  │
  └─────────────────────────────┘
       │
       │  HTTP (port 80)
       ▼
   Browser / Internet
```

### 6.1 — Key Concepts

| Term | What it is |
|---|---|
| **AMI** | Amazon Machine Image — the OS template (like Amazon Linux, Ubuntu) |
| **Instance type** | Hardware config — CPU, RAM (e.g. `t3.micro`) |
| **Key pair** | RSA key used to SSH into the instance — download the `.pem` once, store it safely |
| **Security group** | Firewall rules — controls which ports are open to the internet |
| **User data** | Startup script that runs automatically when the instance first boots |
| **Public IPv4** | The internet-facing address to access your instance |

### Common ports

| Port | Protocol |
|---|---|
| `22` | SSH — connect to the instance terminal |
| `80` | HTTP — web traffic |
| `443` | HTTPS — secure web traffic |
| `8080` | Spring Boot default |
 
---

### 6.2 — Launch an EC2 Instance

1. AWS Console → search **EC2** → **Launch instance**
2. **Name** — `my-first-ec2-server`
3. **AMI** — Amazon Linux 2023
4. **Instance type** — `t3.micro`
5. **Key pair** → **Create new key pair**
    - Name: `my-first-ec2`
    - Type: RSA, format: `.pem`
    - Download and store the `.pem` file safely — you can't download it again
6. **Network settings** → **Create security group** with:
    - Allow SSH from anywhere (`0.0.0.0/0`)
    - Allow HTTP from the internet (`0.0.0.0/0`)
7. **Configure storage** — 8 GiB (default)
8. **Advanced details** → **User data** — paste a startup script to auto-configure the instance on boot:
```bash
#!/bin/bash
yum update -y
yum install -y httpd
systemctl start httpd
systemctl enable httpd
 
echo "<html>
  <head><title>My First AWS Page</title></head>
  <body>
    <h1>Hello from EC2!</h1>
    <p>This is a static HTML page hosted on Apache.</p>
  </body>
</html>" > /var/www/html/index.html
```

9. Review the summary and click **Launch instance**
   **Launch summary:**

| Setting | Value |
|---|---|
| AMI | Amazon Linux 2023 |
| Instance type | `t3.micro` |
| Security group | New (SSH + HTTP) |
| Storage | 1 volume — 8 GiB |

EC2 Dashboard shows **Instances running: 1** ✅
 
---

### 6.3 — Test the Instance

1. Go to the instance → copy the **Public IPv4 address**
2. Paste it in the browser → the Apache page appears ✅
---

### 6.4 — Connect to the Instance via SSH

**Option 1 — Browser (easiest):**

Click the instance → **Connect** button → **EC2 Instance Connect** → opens a terminal in the browser.

**Option 2 — SSH from your terminal:**

```bash
cd Downloads/
 
# Restrict key file permissions (required by SSH)
chmod 400 "my-first-ec2.pem"
 
# Connect
ssh -i "my-first-ec2.pem" ec2-user@ec2-65-0-94-183.ap-south-1.compute.amazonaws.com
```

Once connected, verify the Apache page file:

```bash
cd /var/www/html/
cat index.html
```

Output matches the HTML set in User data ✅

> ⚠️ If you stop or terminate the instance, the SSH connection drops immediately with `Connection reset by peer`.
 
---

### 6.5 — Region Gotcha

If you switch AWS regions in the console after launching, the instance list shows 0 — the instance isn't gone, you're just looking at the wrong region. Use **Global View** (top-right) to see instances across all regions.
 
---

### 6.6 — Stop / Terminate the Instance

- **Stop** — instance is paused, no compute charges, but EBS storage still billed
- **Terminate** — instance is permanently deleted, no further charges
  Go to instance → **Instance state** → **Stop** or **Terminate** when done.

> ⚠️ Always stop or terminate instances you're not using — a running EC2 will consume your free tier hours.
 
---

### EC2 Key Takeaways

| Concept | Rule of thumb |
|---|---|
| AMI | Pick Amazon Linux for AWS-optimised defaults |
| Instance type | `t3.micro` is free-tier eligible — don't go larger without reason |
| Key pair | Store the `.pem` safely — losing it means losing SSH access |
| Security groups | Only open the ports your app actually needs |
| User data | Great for bootstrapping — runs once on first boot as root |
| Terminate vs stop | Stop to pause; terminate to permanently delete |
 
---

## 7 — Deploy Spring Boot App on EC2

### 7.1 — Create the Spring Boot App

```
groupId:    com.utkarsh
artifactId: SpringBootEC2DemoApp
dependency: Spring Web
```

```java
@RestController
public class HelloController {
 
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Spring Boot EC2!";
    }
}
```

Test locally: `http://localhost:8080/hello` → `Hello from Spring Boot EC2!`
 
---

### 7.2 — Launch EC2 Instance

1. Region: Asia Pacific (`ap-south-1`)
2. Name: `spring-boot-app-server`
3. AMI: Amazon Linux 2023
4. Instance type: `t3.micro`
5. Key pair → **Create new** → name `spring-boot-app-key` → RSA → `.pem`
6. Network settings → **Select existing security group** → `launch-wizard-1` (created in [6.2](#62--launch-an-ec2-instance))
7. **Launch instance**
---

### 7.3 — Build the JAR

```bash
.\mvnw clean package
```

Output:

```
BUILD SUCCESS
target/SpringBootEC2DemoApp-0.0.1-SNAPSHOT.jar
```
 
---

### 7.4 — Copy JAR to EC2 (SCP)

Put your `.pem` and `.jar` in the same folder, then open a terminal there:

```bash
scp -i "spring-boot-app-key.pem" SpringBootEC2DemoApp-0.0.1-SNAPSHOT.jar \
  ec2-user@ec2-13-201-123-65.ap-south-1.compute.amazonaws.com:/home/ec2-user/
```

```
SpringBootEC2DemoApp-0.0.1-SNAPSHOT.jar   100%  19MB  3.9MB/s  00:04
```
 
---

### 7.5 — SSH into EC2

```bash
ssh -i "spring-boot-app-key.pem" ec2-user@ec2-13-201-123-65.ap-south-1.compute.amazonaws.com
```

> ⚠️ Run this from the folder containing the `.pem` file, or SSH will fail with `No such file or directory`.

Verify the JAR arrived:

```bash
ls
# SpringBootEC2DemoApp-0.0.1-SNAPSHOT.jar
```
 
---

### 7.6 — Install Java on EC2

Amazon Linux doesn't come with Java pre-installed:

```bash
sudo yum update -y
sudo yum search corretto          # list available Java versions
sudo yum install java-25-amazon-corretto-devel -y
java --version
# openjdk 25.0.2 ... Corretto-25.0.2.10.1
```
 
---

### 7.7 — Run the App

**First attempt — foreground:**

```bash
java -jar SpringBootEC2DemoApp-0.0.1-SNAPSHOT.jar
```

App starts on port 8080, but hitting the URL fails — port 8080 is not open in the security group yet.

**Fix: add inbound rule for port 8080**

AWS Console → EC2 → Security Groups → `launch-wizard-1` → **Edit inbound rules** → Add:

| Type | Port | Source |
|---|---|---|
| Custom TCP | 8080 | 0.0.0.0/0 |

Security group now has 3 rules: SSH (22), HTTP (80), Custom TCP (8080).

Hit `http://ec2-13-201-123-65.ap-south-1.compute.amazonaws.com:8080/hello` → `Hello from Spring Boot EC2!` ✅
 
---

### 7.8 — Keep App Running After Logout (`nohup`)

Closing the terminal kills the app. Use `nohup` to keep it running in the background:

```bash
nohup java -jar SpringBootEC2DemoApp-0.0.1-SNAPSHOT.jar > output.log 2>&1 &
```

| Part | What it does |
|---|---|
| `nohup` | Prevents the process from stopping when the terminal closes |
| `> output.log` | Redirects stdout (app logs) to a file |
| `2>&1` | Also redirects stderr (errors) to the same file |
| `&` | Runs the process in the background |

View logs in real time:

```bash
tail -f output.log
```
 
---

### 7.9 — Common Errors

**`Port 8080 was already in use`**

Running `nohup java -jar` a second time when the first instance is still running:

```bash
# Find the process using port 8080
sudo lsof -i :8080
 
# Kill it
kill -9 <PID>
 
# Then restart
nohup java -jar SpringBootEC2DemoApp-0.0.1-SNAPSHOT.jar > output.log 2>&1 &
```

**`Permission denied (publickey)`**

SSH run from a directory that doesn't contain the `.pem` file:

```bash
cd C:\Users\Utkar\Documents\Code\AWS\EC2   # navigate to the folder with the .pem
ssh -i "spring-boot-app-key.pem" ec2-user@...
```
 
---

### Key Takeaways

| Concept | Note |
|---|---|
| `scp` | Secure copy — transfers files to EC2 over SSH using the same `.pem` key |
| Java not pre-installed | Always install Corretto on a fresh Amazon Linux instance before running a JAR |
| Port 8080 blocked by default | Must add a custom TCP inbound rule in the security group |
| `nohup ... &` | The correct way to keep a process running after SSH logout |
| `tail -f output.log` | Stream live logs from a background process |
| Port conflict | Kill the old process before restarting — check with `lsof -i :8080` |

---

## 8 — Connect to S3 from Spring Boot App Running on EC2

### 8.1 — Why No Access Keys Needed on EC2

When both the Spring Boot app and S3 are on AWS, you don't need to hardcode access keys. Instead, attach an **IAM Role** with S3 permissions to the EC2 instance — the SDK picks up credentials automatically via the instance metadata service (IMDS).

```
  EC2 Instance
  ┌───────────────────────────────┐
  │  Spring Boot App              │
  │  (dev profile)                │
  │  DefaultCredentialsProvider   │
  │       │                       │
  │       │  reads from IMDS      │
  │       ▼                       │
  │  IAM Role (ec2-s3-role)       │
  └───────────────┬───────────────┘
                  │  AmazonS3FullAccess
                  ▼
             S3 Bucket
```
 
---

### 8.2 — Spring Profiles Setup

Use two profiles so the same codebase works locally (with keys) and on EC2 (with role).

**`application.properties`** — shared, no credentials:

```properties
spring.application.name=S3DemoApplication
spring.profiles.active=local
```

**`application-local.properties`** — for local development:

```properties
cloud.aws.credentials.access-key=YOUR_ACCESS_KEY
cloud.aws.credentials.secret-key=YOUR_SECRET_KEY
cloud.aws.region.static=ap-south-1
aws.bucket.name=learning-account-s3-demo-bucket
```

**`application-dev.properties`** — for EC2 (no credentials needed):

```properties
cloud.aws.region.static=ap-south-1
aws.bucket.name=learning-account-s3-demo-bucket
```
 
---

### 8.3 — S3Config with Profile-Specific Beans

The key fix: inject `access-key` and `secret-key` as method parameters on the `local` bean rather than as class-level `@Value` fields. This way Spring only resolves those properties when the `local` profile is active — the `dev` bean never tries to read them.

```java
@Configuration
public class S3Config {
 
    @Value("${cloud.aws.region.static}")
    private String region;
 
    @Bean("s3Client")
    @Profile("local")
    public S3Client s3Client(@Value("${cloud.aws.credentials.access-key}") String accessKey,
                             @Value("${cloud.aws.credentials.secret-key}") String secretKey) {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }
 
    @Bean("s3Client")
    @Profile("dev")
    public S3Client s3ClientDev() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.builder().build())
                .build();
    }
}
```

> ⚠️ `DefaultCredentialsProvider.create()` is deprecated — use `DefaultCredentialsProvider.builder().build()` instead.
 
---

### 8.4 — Build the JAR (Windows)

On Windows, quote the `-D` flag:

```bash
.\mvnw clean package "-Dspring.profiles.active=local"
```

> Without quotes, PowerShell parses `-Dspring` as a flag and fails with `Unknown lifecycle phase`.

Output: `target/S3DemoApplication-0.0.1-SNAPSHOT.jar` — `BUILD SUCCESS`
 
---

### 8.5 — Create the IAM Role for EC2

1. AWS Console → **IAM** → **Roles** → **Create role**
2. Trusted entity → **AWS service** → Use case: **EC2**
3. Attach policy: **AmazonS3FullAccess**
4. Role name: `ec2-s3-role` → **Create role**
---

### 8.6 — Launch EC2 Instance

Region: Asia Pacific (Mumbai) — same as the S3 bucket.

| Setting | Value |
|---|---|
| Name | `springboot-s3-service` |
| AMI | Amazon Linux 2023 |
| Instance type | `t3.micro` |
| Key pair | `springboot-s3-key` (RSA, `.pem`) |
| Security group | `launch-wizard-1` (existing) |
| Storage | 8 GiB |
 
---

### 8.7 — Copy JAR and Connect

```bash
# Set correct permissions on key
chmod 400 "springboot-s3-key.pem"
 
# Copy JAR to EC2
scp -i "springboot-s3-key.pem" S3DemoApplication-0.0.1-SNAPSHOT.jar \
  ec2-user@ec2-35-154-162-208.ap-south-1.compute.amazonaws.com:/home/ec2-user/
 
# SSH in
ssh -i "springboot-s3-key.pem" ec2-user@ec2-35-154-162-208.ap-south-1.compute.amazonaws.com
 
# Install Java
sudo yum update -y && sudo yum install java-25-amazon-corretto-devel -y && java --version
```
 
---

### 8.8 — Run with `dev` Profile

```bash
nohup java -jar S3DemoApplication-0.0.1-SNAPSHOT.jar \
  -Dspring.profiles.active=dev > output.log 2>&1 &
```

Verify startup:

```bash
cat output.log
# The following 1 profile is active: "dev"
# Tomcat started on port 8080
```
 
---

### 8.9 — Attach IAM Role to the EC2 Instance

The app starts but upload fails with:

```
Unable to load credentials from any of the providers in the chain ...
InstanceProfileCredentialsProvider(): Failed to load credentials from IMDS.
```

**Why:** The EC2 instance has no IAM role attached yet — `DefaultCredentialsProvider` has nowhere to get credentials from.

**Fix:**

1. EC2 Console → select the instance → **Actions** → **Security** → **Modify IAM role**
2. Select `ec2-s3-role` → **Update IAM role**
---

### 8.10 — Test

**Upload:**

```
POST  http://ec2-35-154-162-208.ap-south-1.compute.amazonaws.com:8080/upload
Body  → form-data → Key: file (type: File) → select a file
```

Response: `File uploaded successfully` ✅

**Download:**

```
GET  http://ec2-35-154-162-208.ap-south-1.compute.amazonaws.com:8080/download/java_devops_learning_roadmap.html
```

Returns the file content ✅
 
---

### Key Takeaways

| Concept | Note |
|---|---|
| IAM Role on EC2 | The secure way to grant AWS service access — no hardcoded keys |
| `DefaultCredentialsProvider` | Automatically checks IMDS, env vars, profile files — picks whatever is available |
| Profile-scoped `@Value` | Inject credentials as method params on profile-specific beans to avoid startup failures |
| `-Dspring.profiles.active=dev` | Pass active profile at runtime — the JAR itself is environment-neutral |
| Windows PowerShell quoting | Wrap `-D` flags in quotes: `"-Dspring.profiles.active=local"` |
| `InstanceProfileCredentialsProvider failed` | EC2 instance has no IAM role attached — fix via Actions → Security → Modify IAM role |

---
 
## 9 — RDS (Relational Database Service)
 
RDS is a managed database service from AWS — it handles provisioning, patching, backups, and scaling so you can focus on your data rather than the infrastructure.
 
```
  Your App / Local Machine
          │
          │  port 3306 (MySQL)
          ▼
  ┌─────────────────────────┐
  │  AWS RDS Instance       │
  │  MySQL 8.4.8            │
  │  db.t4g.micro           │
  │  (managed by AWS)       │
  └─────────────────────────┘
```
 
---
 
### 9.1 — Create an RDS Instance
 
1. AWS Console → **RDS** → **Create database**
2. **Engine** → MySQL
3. **Creation method** → Full configuration
4. **Template** → Free tier
5. **Availability** → Single-AZ DB instance (1 instance)
| Setting | Value |
|---|---|
| MySQL edition | MySQL Community |
| Engine version | MySQL 8.4.8 |
| DB identifier | `database-1` |
| Master username | `admin` |
| Credentials management | Self managed |
| Master password | `admin1234` |
| Instance type | `db.t4g.micro` (Burstable) |
| Allocated storage | 20 GiB |
| Public access | Yes |
| VPC security group | Create new → name `database-vpc` |
| Monitoring | Database Insights — Standard |
 
6. Click **Create database**
Once created, find the **Endpoint** under **Connectivity & security** — this is your connection URL.
 
---
 
### 9.2 — Connect to RDS from Local
 
**Terminal (MySQL CLI):**
 
```bash
mysql -h database-1.cnw826w4scnz.ap-south-1.rds.amazonaws.com -P 3306 -u admin -p
```
 
**GUI tools (easier):**
 
- **DBeaver** — paste the endpoint as Server Host, enter username and password → Connect
- **MySQL Workbench** — same, paste as Hostname
---
 
### 9.3 — Basic SQL Walkthrough
 
```sql
-- List existing databases
SHOW DATABASES;
-- information_schema, mysql, performance_schema, sys
 
-- Create and switch to a new database
CREATE DATABASE demo_db;
USE demo_db;
 
-- Create a table
CREATE TABLE employee (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    department VARCHAR(50),
    salary     DECIMAL(10,2)
);
 
SHOW TABLES;
-- employee
 
-- Insert sample data
INSERT INTO employee (name, department, salary) VALUES
('Rahul Sharma', 'IT',        55000.00),
('Priya Singh',  'HR',        45000.00),
('Amit Verma',   'Finance',   60000.00),
('Neha Gupta',   'Marketing', 48000.00),
('Arjun Patel',  'IT',        72000.00);
 
-- Query
SELECT * FROM employee;
-- id | name         | department | salary
--  1 | Rahul Sharma | IT         | 55000.00
--  2 | Priya Singh  | HR         | 45000.00
--  3 | Amit Verma   | Finance    | 60000.00
--  4 | Neha Gupta   | Marketing  | 48000.00
--  5 | Arjun Patel  | IT         | 72000.00
 
-- Clean up
DROP TABLE employee;
```
 
---
 
### 9.4 — Delete the RDS Instance
 
1. RDS Console → select `database-1` → **Actions** → **Delete**
2. **Uncheck** — Create final snapshot
3. **Uncheck** — Retain automated backups
4. Confirm and **Delete**
> ⚠️ Always delete RDS instances when done — even a stopped instance accrues storage charges. Free tier only covers 750 hours/month of `db.t2.micro` or `db.t3.micro` usage.
 
---
 
### RDS Key Takeaways
 
| Concept | Note |
|---|---|
| Managed service | AWS handles patching, backups, failover — you manage the data |
| Endpoint | The hostname you use to connect — found under Connectivity & security |
| Public access | Set to Yes for local dev; set to No for production (use VPC instead) |
| Security group | Controls which IPs/services can reach port 3306 |
| Free tier | `db.t3.micro`, 20 GiB storage, 750 hrs/month — don't go larger |
| Delete carefully | Uncheck snapshot options if you don't need them — avoids lingering storage costs |

# 10 