import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { AngularMaterialModule } from '../angularMaterialModule';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../Services/Auth/auth.service';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-sign-up',
  standalone: true,
  imports: [AngularMaterialModule, CommonModule, ReactiveFormsModule],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.scss'
})
export class SignUpComponent {

  signupform !: FormGroup;
  hidepassword : boolean = true;

  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private authService: AuthService,
    private route: Router
  ){}

  ngOnInit(): void {
    this.signupform = this.fb.group({
      name: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]],
      confirmPassword: [null, Validators.required]
    })
  }

  togglePasswordVisibility(){
    this.hidepassword = !this.hidepassword;
  }

  onSubmit(): void{
    const password = this.signupform.get('password')?.value;
    const confirmPassword = this.signupform.get('confirmPassword')?.value;

    if(password !== confirmPassword){
      this.snackBar.open('password does not match', 'close', {duration: 5000, panelClass: 'error-snackbar'});
      return ;
    }

    this.authService.register(this.signupform.value).subscribe(
      (response) => {
        this.snackBar.open('Signup Sucessful', 'Close', {duration: 5000});
        this.route.navigateByUrl("/login");
      },
      (error) => {
        this.snackBar.open('Signup failed. Please try again', 'Close', {duration: 5000,  panelClass: 'error-snackbar'});
      }
    )
    
  }
}
