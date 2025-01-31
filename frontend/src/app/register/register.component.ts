import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../Services/Auth/auth.service';

@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  signupform!: FormGroup;
  hidePassword: boolean = true;
  hideConfirmPassword: boolean = true;

  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private authService: AuthService,
    private route: Router
  ) { }

  ngOnInit(): void {
    this.signupform = this.fb.group({
      name: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required, Validators.minLength(8)]],
      confirmPassword: [null, Validators.required]
    });
  }

  togglePasswordVisibility(field: string): void {
    if (field === 'password') {
      this.hidePassword = !this.hidePassword;
    } else if (field === 'confirmPassword') {
      this.hideConfirmPassword = !this.hideConfirmPassword;
    }
  }

  onSubmit(): void {
    const password = this.signupform.get('password')?.value;
    const confirmPassword = this.signupform.get('confirmPassword')?.value;

    if (password !== confirmPassword) {
      this.snackBar.open('Password does not match', 'Close', { duration: 5000, panelClass: 'error-snackbar' });
      return;
    }

    const signupRequest = {
      name: this.signupform.get('name')?.value,
      email: this.signupform.get('email')?.value,
      password: this.signupform.get('password')?.value
      
    };

    this.authService.register(signupRequest).subscribe({
      next: (response) => {
        this.snackBar.open('Signup Successful', 'Close', { duration: 5000 });
        this.route.navigateByUrl("/login");
      },
      error: (error) => {
        console.error('Signup error:', error);
        const errorMessage = error.error?.message || 'Signup failed. Please try again.';
        this.snackBar.open(errorMessage, 'Close', { duration: 5000, panelClass: 'error-snackbar' });
      }
    })    
  }
}