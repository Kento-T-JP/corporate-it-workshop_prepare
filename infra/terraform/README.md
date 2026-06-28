# Terraform infrastructure

This directory contains the initial Terraform configuration for AWS resources.

Current scope:

- Amazon ECR repository for the Spring Boot Docker image

## Commands

```bash
terraform init
terraform fmt
terraform validate
terraform plan
```

Do not run `terraform apply` until the AWS account, IAM permissions, and expected cost scope are confirmed.
