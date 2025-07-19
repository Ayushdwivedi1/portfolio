import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-hero',
  templateUrl: './hero.component.html',
  styleUrls: ['./hero.component.scss'],
  standalone: true,
  imports: [CommonModule]
})
export class HeroComponent implements OnInit {
  professions = [
    'Java Developer',
    'Spring Boot Developer',
    'Microservices Architect',
    'Full Stack Developer',
    'Software Engineer'
  ];
  
  currentProfession = '';
  currentIndex = 0;
  charIndex = 0;
  isDeleting = false;

  constructor() { }

  ngOnInit(): void {
    setTimeout(() => {
      this.typeText();
    }, 1000);
  }

  typeText(): void {
    const currentProfession = this.professions[this.currentIndex];
    
    if (this.isDeleting) {
      this.currentProfession = currentProfession.substring(0, this.charIndex - 1);
      this.charIndex--;
    } else {
      this.currentProfession = currentProfession.substring(0, this.charIndex + 1);
      this.charIndex++;
    }

    let typeSpeed = 150;

    if (this.isDeleting) {
      typeSpeed /= 2;
    }

    if (!this.isDeleting && this.charIndex === currentProfession.length) {
      typeSpeed = 2000; // Pause at end
      this.isDeleting = true;
    } else if (this.isDeleting && this.charIndex === 0) {
      this.isDeleting = false;
      this.currentIndex = (this.currentIndex + 1) % this.professions.length;
      typeSpeed = 500; // Pause before starting next word
    }

    setTimeout(() => this.typeText(), typeSpeed);
  }
} 