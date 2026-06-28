variable "aws_region" {
  description = "AWS region where resources are created."
  type        = string
  default     = "ap-northeast-1"
}

variable "ecr_repository_name" {
  description = "Name of the ECR repository for the Spring Boot application image."
  type        = string
  default     = "corporate-it-workshop"
}
