import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService, AuthResponse } from '../../services/auth.service';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './contact.html',
  styleUrls: ['./contact.scss']
})
export class ContactComponent implements OnInit {
  currentUser: AuthResponse | null = null;
  isLoggedIn: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.isLoggedIn = this.authService.isLoggedIn();
    this.currentUser = this.authService.getCurrentUser();
    
    if (!this.isLoggedIn) {
      this.router.navigate(['/login']);
    }
  }

  logout() {
    this.authService.logout().subscribe({
      next: (message) => {
        console.log('Logout successful:', message);
        this.router.navigate(['/home']);
      },
      error: (error) => {
        console.error('Logout error:', error);
        // Still navigate to home even if logout API fails
        this.router.navigate(['/home']);
      }
    });
  }

  onSubmit() {
    // Handle form submission
    alert('Message sent successfully!');
  }

  get userDisplayName(): string {
    if (this.currentUser) {
      if (this.currentUser.firstName && this.currentUser.lastName) {
        return `${this.currentUser.firstName} ${this.currentUser.lastName}`;
      } else if (this.currentUser.firstName) {
        return this.currentUser.firstName;
      } else {
        return this.currentUser.username;
      }
    }
    return '';
  }
} 