// =========================================================
// Mobile nav toggle
// =========================================================
const navToggle = document.querySelector('.nav-toggle');
const nav = document.querySelector('.nav');

if (navToggle && nav) {
  navToggle.addEventListener('click', () => {
    nav.classList.toggle('open');
  });

  document.querySelectorAll('.nav-links a').forEach((link) => {
    link.addEventListener('click', () => nav.classList.remove('open'));
  });
}

// =========================================================
// Scroll reveal
// =========================================================
const revealEls = document.querySelectorAll('.reveal');

if ('IntersectionObserver' in window && revealEls.length) {
  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add('visible');
          observer.unobserve(entry.target);
        }
      });
    },
    { threshold: 0.15 }
  );

  revealEls.forEach((el) => observer.observe(el));
} else {
  revealEls.forEach((el) => el.classList.add('visible'));
}

// =========================================================
// Hero terminal typing effect
// =========================================================
const typedCommandEl = document.getElementById('typed-command');
const terminalResponseEl = document.getElementById('terminal-response');

function typeText(el, text, speed, onDone) {
  let i = 0;
  el.textContent = '';
  const cursor = document.createElement('span');
  cursor.className = 'cursor';
  el.after(cursor);

  const prefersReducedMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches;
  if (prefersReducedMotion) {
    el.textContent = text;
    cursor.remove();
    if (onDone) onDone();
    return;
  }

  const interval = setInterval(() => {
    el.textContent = text.slice(0, i + 1);
    i += 1;
    if (i >= text.length) {
      clearInterval(interval);
      cursor.remove();
      if (onDone) onDone();
    }
  }, speed);
}

if (typedCommandEl && terminalResponseEl) {
  const command = typedCommandEl.textContent.trim();
  typeText(typedCommandEl, command, 35, () => {
    setTimeout(() => {
      terminalResponseEl.classList.add('visible');
    }, 150);
  });
}

// =========================================================
// Auto-hide flash / success banners
// =========================================================
document.querySelectorAll('.form-success, .admin-flash').forEach((banner) => {
  setTimeout(() => {
    banner.style.transition = 'opacity 0.4s ease';
    banner.style.opacity = '0';
    setTimeout(() => banner.remove(), 400);
  }, 4500);
});

// =========================================================
// Button ripple effect (adds the element the CSS .ripple / @keyframes
// ripple rules expect - click position becomes the ripple origin)
// =========================================================
document.querySelectorAll('.btn').forEach((btn) => {
  btn.addEventListener('click', function (e) {
    const rect = this.getBoundingClientRect();
    const ripple = document.createElement('span');
    ripple.className = 'ripple';
    ripple.style.left = `${e.clientX - rect.left}px`;
    ripple.style.top = `${e.clientY - rect.top}px`;
    this.appendChild(ripple);
    ripple.addEventListener('animationend', () => ripple.remove());
  });
});